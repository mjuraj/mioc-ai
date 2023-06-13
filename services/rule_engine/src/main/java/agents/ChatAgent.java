package agents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.payload.HistoryItem;
import com.mindsmiths.calendarAdapter.api.Timeslot;
import com.mindsmiths.dashboard.models.AssistantConfiguration;
import com.mindsmiths.dashboard.models.Configuration;
import com.mindsmiths.gpt3.GPT3AdapterAPI;
import com.mindsmiths.gpt3.chatCompletion.ChatCompletionModel;
import com.mindsmiths.gpt3.chatCompletion.ChatCues;
import com.mindsmiths.gpt3.chatCompletion.ChatMessage;
import com.mindsmiths.gpt3.completion.Complete;
import com.mindsmiths.gpt3.completion.CompletionModel;
import com.mindsmiths.infobipAdapter.InfobipAdapterAPI;
import com.mindsmiths.infobipAdapter.api.Button;
import com.mindsmiths.infobipAdapter.api.MessageWithButtons;
import com.mindsmiths.infobipAdapter.api.MessageWithInteractiveList;
import com.mindsmiths.infobipAdapter.api.Row;
import com.mindsmiths.mitems.Element;
import com.mindsmiths.mitems.ElementType;
import com.mindsmiths.mitems.Mitems;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.sdk.core.db.Database;
import com.mindsmiths.sdk.utils.Utils;
import com.mindsmiths.sdk.utils.serialization.Mapper;
import com.mindsmiths.sdk.utils.store.DistributedCache;
import com.mindsmiths.sdk.utils.templating.Templating;
import com.mindsmiths.textembedder.TextEmbedderAPI;
import com.mongodb.client.model.Filters;
import lombok.Data;
import signals.Reminder;
import utils.ArmoryUtil;
import utils.MitemsUtil;
import utils.Settings;
import utils.TimeFormatUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class ChatAgent extends Agent {

    // Communication
    public List<String> messages = new ArrayList<>();
    public List<Reminder> reminders = new ArrayList<>();
    String previousContext = "";
    AssistantConfiguration assistantConfiguration;

    // Scheduling
    String timeZone = Settings.DEFAULT_TIME_ZONE;
    String schedulingAgentId;
    String schedulingFlow;
    String schedulingMessageId;

    // HITL
    boolean hitlProtocol;
    LocalDateTime lastHitlReplyAt;
    String hitlChannelId;

    // Prompt logging
    public static final int PROMPT_EXPIRE_TIME_SEC = 3600 * 24;
    public static final String PROMPT_PREFIX = "prompt-";
    public List<String> promptOrder = new ArrayList<>();


    // GPT Communication

    public void savePromptForLogs(List<String> prompt) {
        String promptId = Utils.randomString(8);
        promptOrder.add(promptId);
        DistributedCache.getInstance().set(PROMPT_PREFIX + promptId, prompt, PROMPT_EXPIRE_TIME_SEC);
    }

    public String popPromptId() {
        String promptId = promptOrder.get(0);
        promptOrder.remove(0);
        return promptId;
    }

    public List<String> getPromptFromCache(String promptId) {
        return DistributedCache.getInstance().get(PROMPT_PREFIX + promptId,
                List.of("Sorry, the data is no longer available."), List.class);
    }

    public List<String> generatePromptForLogs(String persona, String context, String instruction) {
        List<String> promptElements = new ArrayList<>();

        promptElements.add("** Persona: **\n" + persona.trim() + "\n");

        if (context.length() > 2000) {
            promptElements.add("\n** Knowledge base results: **\n");
            for (String line: context.split("\n")) {
                String formattedLine = line.trim().replace("```","");
                if (!formattedLine.isEmpty())
                    promptElements.add("```" + formattedLine + "```\n");
            }
        } else {
            promptElements.add("\n** Knowledge base results: **\n" + context.trim() + "\n");
        }

        promptElements.add("\n** Instructions: **\n" + instruction.trim() + "\n");
        promptElements.add("\n** Message history: **\n" + String.join("\n", messages).trim() + "\nAssistant: ");


        return promptElements;
    }

    public void askGPT(String context) {
        assistantConfiguration.setIntroText(Templating.recursiveRender(assistantConfiguration.getIntroText(), fillContext()));
        assistantConfiguration.setQaPrompt(Templating.recursiveRender(assistantConfiguration.getQaPrompt(), fillContext()));

        String contextText = Mitems.getText("prompts.prompt-elements.context-en");
        String punctText = Mitems.getText("prompts.prompt-elements.punctuation-en");
        if (assistantConfiguration.getLanguageCode().equalsIgnoreCase("hr")) {
            contextText = Mitems.getText("prompts.prompt-elements.context-hr");
            punctText = Mitems.getText("prompts.prompt-elements.punctuation-hr");
        }

        String formattedContext = this.generatePromptContext(context);
        String prompt = assistantConfiguration.getIntroText() + String.format("\n\n%s: ```\n%s\n```\n\n", contextText, formattedContext) + assistantConfiguration.getQaPrompt() + "\n" + punctText + "\n";
        this.previousContext = context;
        savePromptForLogs(generatePromptForLogs(assistantConfiguration.getIntroText(),
                String.format("\n\n%s: ```\n%s\n```\n\n", contextText, formattedContext),
                assistantConfiguration.getQaPrompt()));

        if (assistantConfiguration.getModel().equals("GPT-3")) {
            simpleGPT3Request(prompt, assistantConfiguration);
        } else {
            simpleChatGPTRequest(prompt, assistantConfiguration);
        }
    }

    public String generatePromptContext(String context) {

        List<String> currContext = new ArrayList<>(Arrays.asList(context.split("\n")));
        List<String> prevContext = new ArrayList<>();

        if (!previousContext.equals("")) {
            String[] previousContextList = previousContext.split("\n");
            if (previousContextList.length > 3)
                prevContext = new ArrayList<>(Arrays.asList(previousContextList).subList(0, 3));
            else
                prevContext = new ArrayList<>(Arrays.asList(previousContextList));
        }

        return this.formatContext(prevContext, currContext);
    }

    public String formatContext(List<String> prevContext, List<String> currContext) {
        Set<String> contextSet = new HashSet<>();
        contextSet.addAll(prevContext);
        contextSet.addAll(currContext);
        List<String> list = new ArrayList<>(contextSet);
        return list.stream().collect(Collectors.joining("\n"));
    }

    public Map<String, Object> fillContext() {
        HashMap<String, Object> context = new HashMap<>();
        context.put("url", this.getArmoryUrl());
        context.put("onboardingUrl", this.getArmoryUrl("onboarding"));
        context.put("currentDateTime", TimeFormatUtils.toZonedDateTime(Utils.now(), timeZone).format(DateTimeFormatter.ofPattern("EE, yyyy-MM-dd HH:mm:ss z")));
        return context;
    }

    public String getArmoryUrl() {
        return Settings.ARMORY_SITE_URL + "/" + getConnection("armory");
    }

    public String getArmoryUrl(String feature) {
        return Settings.ARMORY_SITE_URL + "/" + getConnection("armory") + "?f=" + feature;
    }

    public void searchEmbeddings(String query) {
        List<String> sourceIds = assistantConfiguration.getKnowledgeBaseIds();
        TextEmbedderAPI.search(query, 5, sourceIds);
    }

    public Reminder addReminder(String reminderString) {
        try {
            Log.info("Adding reminder: " + reminderString);
            Reminder reminder = Mapper.getReader(Reminder.class).readValue(reminderString);
            reminders.add(reminder);
            return reminder;
        } catch (Exception e) {
            Log.error("Failed to add reminder: " + reminderString);
            return null;
        }
    }

    public void processReminderQuery(String text) {
        String reminderPrompt = "Return a JSON file with two fields: 'reminder' and 'dateTime'. The 'reminder' field should contain " +
            "the text of the reminder. The 'dateTime' field should contain the time of the reminder in the ISO format and in UTC. " +
            "For example, if today is May 19th 2023, the user is in timezone Europe/Zagreb, and the message is " +
            "'Remind me to buy milk tomorrow at seven thirty PM', then the output should be: " +
            "{\"reminder\": \"Buy milk\", \"dateTime\": \"2023-05-20T17:30:00.000000Z\"}.\n\n" +
            "Today is " + Utils.now() + " and the user is in timezone " + timeZone + ". " +
            "Use the following message from the user: '" + text + "'";
        rawGPT3Request(reminderPrompt, 0);
    }

    public Complete rawGPT3Request(String prompt, double temperature) {
        return GPT3AdapterAPI.complete(prompt, CompletionModel.text_davinci_003,
            2000, // max tokens
            temperature // temperature
        );
    }

    public void simpleGPT3Request(String prompt, AssistantConfiguration config) {
        prompt = prompt + String.join("\n", messages) + "\nAssistant: ";

        Log.info("GPT prompt: " + prompt);

        GPT3AdapterAPI.complete(
                prompt, // input prompt
                CompletionModel.text_davinci_003, // model
                config.getMaxTokens(), // max tokens
                config.getTemperature(), // temperature
                config.getTopP(), // topP
                1, // N
                null, // logprobs
                false, // echo
                List.of("User:", "Assistant:"), // STOP words
                0.6, // presence penalty
                0.0, // frequency penalty
                1, // best of
                null); // logit bias
    }

    public void simpleChatGPTRequest(String intro, AssistantConfiguration config) {
        List<ChatMessage> prompt = new ArrayList<>();
        prompt.add(new ChatMessage(ChatCues.SYSTEM.label, intro));

        for (String message : messages) {
            ChatCues cue = (message.startsWith("User")) ? ChatCues.USER : ChatCues.ASSISTANT;
            prompt.add(new ChatMessage(cue.label, message.replace("User:","").replace("Assistant:","").trim()));
        }

        Log.info("GPT prompt: " + prompt);

        String model = (config.getModel().equals("GPT-3.5")) ? ChatCompletionModel.gpt_turbo : ChatCompletionModel.chatGPT;
        GPT3AdapterAPI.chatComplete(
                prompt, // input prompt
                model, // model
                config.getMaxTokens(), // max tokens
                config.getTemperature(), // temperature
                config.getTopP(), // topP
                1, // N
                List.of(ChatCues.USER.label, ChatCues.ASSISTANT.label), // STOP words
                0.6, // presence penalty
                0.0, // frequency penalty
                null // logit bias
        );

    }

    public String pickRandomMitemsText(String mitemsSlug) {
        List<Element> mitemsElements = Mitems.getItem(mitemsSlug).getElements();
        List<String> textOptions = new ArrayList<>();
        for (Element element : mitemsElements) {
            if (element.getType() == ElementType.TEXT) {
                textOptions.add(element.getContent());
            }
        }
        if (textOptions.isEmpty())
            return "";
        return Utils.randomChoice(textOptions);
    }


    public void sendMessage(String text) {
        sendMessage(text, false);
    }

    public void sendContextMessage(String text) {
        sendMessage(Templating.recursiveRender(text, fillContext()), false);
    }

    public void sendMessage(String text, boolean memorize) {
        InfobipAdapterAPI.sendWhatsappTextMessage(getConnection("phone"), text);
        if (memorize)
            messages.add("Assistant: " + text.trim());
    }

    public void sendMessageWithButtons(String text, List<Button> buttons) {
        MessageWithButtons content = new MessageWithButtons(text, buttons);
        InfobipAdapterAPI.sendWhatsappMessage(getConnection("phone"), content,
            "message/interactive/buttons");
    }


    public void sendMessageWithButtons(String text, String mitemsOptionSlug, Map<String, Object> context) {
        sendMessageWithButtons(text, MitemsUtil.optionsToButtons(Mitems.getOptions(mitemsOptionSlug), context));
    }

    public void reset() {
        messages.clear();
        reminders.clear();
        previousContext = "";
    }

    public AssistantConfiguration findAssistant(String name) {
        return Database.get(Filters.regex("name", "^" + name + "$", "i"), AssistantConfiguration.class);
    }

    public void switchAgent(AssistantConfiguration config) {
        reset();
        setAssistantConfiguration(config);
    }

    // Armory

    public void show(String firstScreen) throws ClassNotFoundException, JsonProcessingException {
        show(firstScreen, null);
    }

    public void show(String firstScreen, Map<String, Object> context) throws ClassNotFoundException, JsonProcessingException {
        show(firstScreen, context, new ArrayList<>());
    }

    public void show(String firstScreen, Map<String, Object> context, List<HistoryItem> history) throws ClassNotFoundException, JsonProcessingException {
        JsonNode screens = Database.get(Filters.exists("screens"), Configuration.class).getScreens();
        Map<String, Object> configuration = new HashMap<>();
        configuration.put("persistentHistory", true);
        ArmoryUtil.show(getConnection("armory"), firstScreen, context, screens, history, configuration);
    }

    public void showScreens(Screen... screens) {
        ArmoryAPI.show(getConnection("armory"), screens);
    }

    public List<Timeslot> chooseTimeslots(List<Timeslot> timeslots, int numberOfSlots) {
        if (timeslots.size() <= numberOfSlots)
            return timeslots;
        List<Timeslot> finalTimeslots = new ArrayList<>();
        Random random = new Random();
        Map<LocalDate, List<Timeslot>> timeslotsByDate = timeslots.stream().collect(Collectors.groupingBy((Timeslot t) -> t.getStart().toLocalDate()));
        while (finalTimeslots.size() < numberOfSlots) {
            for (LocalDate date : timeslotsByDate.keySet()) {
                if (finalTimeslots.size() >= numberOfSlots) break;
                if (timeslotsByDate.get(date).size() == 0) continue;
                int index = timeslotsByDate.get(date).size() > 1 ? random.nextInt(timeslotsByDate.get(date).size()) : 0;
                finalTimeslots.add(timeslotsByDate.get(date).remove(index));
            }
        }
        finalTimeslots.sort(Comparator.comparing(Timeslot::getStart));
        return finalTimeslots;
    }

    public void sendSchedulingMessage(String text, String title, List<Timeslot> timeslots) {
        timeslots = chooseTimeslots(timeslots, 10);
        Map<LocalDate, List<Timeslot>> timeslotMap = timeslots.stream().collect(Collectors.groupingBy((t) -> TimeFormatUtils.toZonedDateTime(t.getStart(), timeZone).toLocalDate()));
        List<MessageWithInteractiveList.Section> sections = new ArrayList<>();
        for (LocalDate date : timeslotMap.keySet().stream().limit(10).sorted().toList())
            sections.add(new MessageWithInteractiveList.Section(TimeFormatUtils.formatDate(date),
                timeslotMap.get(date).stream().limit(10).map(timeslot -> new Row(Utils.datetimeToStr(timeslot.getStart()),
                    TimeFormatUtils.formatTimeslot(timeslot, timeZone), null)).toList()));
        schedulingMessageId = InfobipAdapterAPI.sendWhatsappMessage(getConnection("phone"),
            new MessageWithInteractiveList(Templating.recursiveRender(text, fillContext()),
                title, sections), "message/interactive/list").getId();
    }

}
