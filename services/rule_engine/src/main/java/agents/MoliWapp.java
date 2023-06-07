package agents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.Description;
import com.mindsmiths.armory.component.CustomComponent;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.Image;
import com.mindsmiths.armory.component.SubmitButton;
import com.mindsmiths.armory.component.TextArea;
import com.mindsmiths.armory.component.Title;
import com.mindsmiths.gpt3.GPT3AdapterAPI;
import com.mindsmiths.infobipAdapter.InfobipAdapterAPI;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.sdk.utils.Utils;

import config.Settings;
import lombok.*;

import com.mindsmiths.infobipAdapter.api.Button;
import com.mindsmiths.infobipAdapter.api.MessageType;
import com.mindsmiths.infobipAdapter.api.WhatsappMessage;
import com.mindsmiths.infobipAdapter.api.MessageWithButtons.Action;
import signals.MessageContent;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class MoliWapp extends Agent {

    String gender;
    Integer age;
    Integer rating;
    String feedback;
    String response;

    // TODO: Import resetting and forgetting memory.
    private List<String> memory = new ArrayList<>();
    private int MAX_MEMORY = 3;

    public boolean reminderSent;
    public LocalDateTime lastNpsSent;


    public String getArmoryUrl() {
        // Log.info(Settings.ARMORY_SITE_URL + "/" + getConnection("armory"));
        return Settings.ARMORY_SITE_URL + "/" + getConnection("armory");
    }

    public MoliWapp(String phone) {
        this.id = phone;
        setConnection("infobip", phone);
        setConnection("armory", Utils.randomString());
    }

    public MoliWapp(String phone, String gender, Integer age) {
        this(phone);
        // TODO: Maybe you will need this to use setGender() or setAge()
        // to trigger rules.
        this.gender = gender;
        this.age = age;
    }

    public void askForSurveyResponse() {
        String intro = String.format("You are Moli, an AI agent. Write me a simple, one or two sentences long, response that must be in Croatian language where you thank a highschool student for finishing the survey. Make it very friendly, but generic. Do not add any names.");
        simpleGPT3Request(intro);
    }

    public void simpleGPT3Request(String prompt) {
        Log.info("Prompt for GPT-3:\n" + prompt);
        GPT3AdapterAPI.complete(
            prompt, // input prompt
            "text-davinci-003", // model
            150, // max tokens
            0.9, // temperature
            1.0, // topP
            1, // N
            null, // logprobs
            false, // echo
            List.of("Human:", "Moli:"), // STOP words
            0.6, // presence penalty
            0.0, // frequency penalty
            1, // best of
            null // logit bias
        );
    }

    private void trimMemory() {
        if (memory.size() > MAX_MEMORY + 1)
            memory = memory.subList(memory.size() - 1 - MAX_MEMORY, memory.size());
    }

    public void handleWhatsappMessage(WhatsappMessage message) {
        Log.info("Received a whatsapp message; type: " + message.getType() + "; signal: " + message);
        if (message.getType() == MessageType.INTERACTIVE_BUTTON_REPLY) {
            sendButtons();
        } else {

            // TODO: Add memory to the prompt.
            String prompt = "You are Moli, an AI agent helping students about MIOC highschool, only talk in Croatian. You can only talk about these topics: raspored, učionice, događaji u školi, stručna služba. In case the message isn't about these topics, reply with 'Ne mogu odgovoriti na ovo pitanje. Mogu li ti pomoći s nečim drugim?'.\n Human: <message>\nMoli: response\n";
            simpleGPT3Request(prompt);
            addMessageToMemory("Human", message.getText());
        }
    }

    // TODO: A generic is needed here for all types of messages,
    // media, buttons etc.
    public void sendWhatsappTextMessage(String message) {
        InfobipAdapterAPI.sendWhatsappTextMessage(getConnection("infobip"), message);
    }

    public void sendButtons() {
        MessageContent msg = new MessageContent(
            "Bok!\nJa sam Moli, miočanski Al asistent! Možeš me kontaktirati za razne stvari, a ja ću ti pokušati pomoći tako da ti u školi bude TOP!", //TODO: Ovo je samo za testing, napisati introduction na Moli
            new Action(List.of(
                new Button("GET_ARMORY_LINK", "Idemo!"))));

        InfobipAdapterAPI.sendWhatsappMessage(getConnection("infobip"), msg, "message/interactive/buttons");
    }

    public void addMessageToMemory(String sender, String text){
        memory.add(String.format("%s: %s\n", sender, text));
        trimMemory();
    }

    public void sendNpsMessage() {
        MessageContent msg = new MessageContent(
            "Bok!\nJa sam Moli, miočanski Al asistent! Možeš me kontaktirati za razne stvari, a ja ću ti pokušati pomoći tako da ti u školi bude TOP!", //TODO: Ovo je samo za testing, napisati introduction na Moli
            new Action(List.of(
                new Button("GET_ARMORY_LINK", "Idemo!"))));

        InfobipAdapterAPI.sendWhatsappMessage(getConnection("infobip"), msg, "message/interactive/buttons");

        String nps = "Hej, trebam tvoju pomoc!\nŽelim vidjeti što mogu učiniti da ti bude bolje u MIOC-u i zato provodim kratku anketu. Možeš ju ispuniti?\n" + getArmoryUrl();
        sendWhatsappTextMessage(nps);
    }

    // String nps = "Hej, trebam tvoju pomoc!\nŽelim vidjeti što mogu učiniti da ti bude bolje u MIOC-u i zato provodim kratku anketu. Možeš ju ispuniti?" + getArmoryLink(); //TODO: Ovo je samo za testing, napisati poruku gdje se prilaze NPS armory link, lijepse formatirati link
    String reminderMessage = "Vidim da još nisi ispunio/la anketu. Možeš li ju sada ispuniti? Hvala ti!"; //TODO: Ovo je samo za testing, napisati 

    public void handleButtonResponse(String id) {
        if(id == "GET_ARMORY_LINK") {
            // sendWhatsappTextMessage(nps);
            sendNpsMessage();
        }
    }

    public void showNPSFlow() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen("Welcome")
                .add(new Header("logo.png", false))
                .add(new Image("public/sovica.png", true))
                .add(new Title("Prvo kratko upoznavanje, a onda želim čuti tvoje mišljenje ;)"))
                .add(new SubmitButton("welcomeStarted", "Idemo!", "askForGender")),
            new Screen("askForGender")
                .add(new Header("logo.png", true)) 
                .add(new Title("Jesi li miočanin ili miočanka?")) 
                .add(new Image("public/sovica.png", true))
                .add(new SubmitButton("askForGenderStartedm", "Miočanin", "askForAge")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForGenderStartedf", "Miočanka", "askForAge")),
            new Screen("askForAge")
                .add(new Header("logo.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new Image("public/sovica.png", true))
                .add(new SubmitButton("askForAgeStarted1", "Prvi", "askForRating")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgeStarted2", "Drugi", "askForRating"))
                .add(new SubmitButton("askForAgeStarted3", "Treći", "askForRating"))
                .add(new SubmitButton("askForAgeStarted4", "Četvrti", "askForRating")),
            new Screen("askForRating")
                .add(new Header("logo.png", true)) 
                .add(new Title("Kolika je vjerojatnost da bi preporučio MIOC frendu ili frendici?"))
                .add(new Description("Označi odgovor na skali od 0 do 10. 0 znači da ne bi uopće preporučio, a 10 da bi sigurno preporučio."))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingStarted", "Idemo!", "askForFeedback")), //dodaj slider
            new Screen("askForFeedback")
                .add(new Header("logo.png", true))
                .add(new Title("Zašto?"))
                .add(new Description("Slobodno napiši  zašto si se odlučio za tu ocjenu i što možemo učiniti da bi ona bila bolja."))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackStarted", "Pošalji", "endScreen")),
            new Screen("endScreen")
                .add(new Image("public/srce.png", false))
                .add(new Title("Tvoj odgovor je poslan!"))
                .add(new Description(response))
        );
    }
}
