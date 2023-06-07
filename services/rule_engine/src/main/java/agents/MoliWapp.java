package agents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.Description;
import com.mindsmiths.armory.component.CloudSelect;
import com.mindsmiths.armory.component.CustomComponent;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.Image;
import com.mindsmiths.armory.component.Input;
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
import com.mindsmiths.infobipAdapter.api.MessageWithButtons;
import com.mindsmiths.infobipAdapter.api.WhatsappMessage;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class MoliWapp extends Agent {

    String name;
    String gender;
    Integer age;
    Integer rating;
    String feedback;
    String classLetter;

    private List<String> memory = new ArrayList<>();
    private int MAX_MEMORY = 3;

    public boolean reminderSent;
    public LocalDateTime lastNpsSent;

    String reminderMessage = "Vidim da još nisi ispunio/la anketu. Možeš li ju sada ispuniti? Hvala ti!";


    public String getArmoryUrl() {
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

    private void simpleGPT3Request(String prompt) {
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

    public void addMessageToMemory(String sender, String text) {
        memory.add(String.format("%s: %s\n", sender, text));
        trimMemory();
    }

    public void sendWhatsappTextMessage(String message) {
        InfobipAdapterAPI.sendWhatsappTextMessage(getConnection("infobip"), message);
    }

    public void handleWhatsappTextMessage(WhatsappMessage message) {
        //Log.info("Received message: text: " + message.getText() + "; message: " + message);
        // TODO: Maybe you want more (specific) trigger words for sending the global menu.
        if (message.getText().equalsIgnoreCase("moli") || message.getText().equalsIgnoreCase("izbornik")) {
            sendGlobalMenu();
        } else {
            // TODO: Consider this prompt. What should Moli do?
            // It also needs to have connection to the MIOC knowledge, so it can respond to these topics.
            // Raspored is low priority.
            String prompt = "You are Moli, an AI agent helping students about high school called MIOC, only talk in Croatian. " +
            "Only talk about these topics: raspored, učionice, događaji u školi, stručna služba. " +
            "If message is outside the scope, reply with 'Ne mogu odgovoriti na ovo pitanje. Mogu li ti pomoći s nečim drugim?'.\n";
            simpleGPT3Request(prompt + String.join("\n", memory) + "Human: " + message.getText() + "\nMoli:");
            addMessageToMemory("Human", message.getText());
        }
    }
    
    public void sendGlobalMenu() {
        MessageWithButtons msg = new MessageWithButtons(
            "Bok!\nJa sam Moli, miočanski AI asistent! Možeš me kontaktirati za razne stvari, a ja ću ti pokušati pomoći tako da ti u školi bude 🔝!",
            List.of(
                new Button("GET_ARMORY_LINK", "Idemo!")
            )
        );

        InfobipAdapterAPI.sendWhatsappMessage(getConnection("infobip"), msg, "message/interactive/buttons");
    }

    public void askForThanksMessage() {
        // TODO: Rewrite the prompt in a better way. This gives subpar results.
        String prompt = "You are Moli, an AI agent. Write a simple, one or two sentences long, response in Croatian" +
        " where you thank " + name + 
        ", a highschool student for finishing the survey. Ask student if it needs help with anything.";
        simpleGPT3Request(prompt);
    }

    public void sendNpsMessage() {
        String nps = "Trebam tvoju pomoć! 👋\n" +
        "Želim vidjeti što mogu učiniti da ti bude bolje u MIOC-u i zato provodim kratku anketu. " +
        "Možeš ju ispuniti?\n" + getArmoryUrl();
        sendWhatsappTextMessage(nps);
    }

    public void showNPSFlow() {
        List<Screen> screens = new ArrayList<>();
        if (name == null || name.isEmpty()) {
            Collections.addAll(
                screens,
                new Screen("Welcome")
                    .add(new Header("logo.png", false))
                    .add(new Image("public/sovica.png", true))
                    .add(new Title("Prvo kratko upoznavanje, a onda želim čuti tvoje mišljenje ;)"))
                    .add(new SubmitButton("welcomeStarted", "Idemo!", "askForName")),
                new Screen("askForName")
                    .add(new Header("logo.png", false))
                    .add(new Image("public/sovica.png", true))
                    .add(new Title("Koje je tvoje ime?"))
                    .add(new Input("name", "Moli", true))
                    .add(new SubmitButton("askForNameFinished", "Nastavimo!", "askForGender")),
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
                    .add(new SubmitButton("askForAgeStarted4", "Četvrti", "askForClassLetter")),
                new Screen("askForClassLetter")
                    .add(new Header("logo.png", true)) 
                    .add(new Title("Koje slovo?"))
                    .add(new Description("Odaberi samo jedno slovo."))
                    .add(new Image("public/sovica.png", true))
                    .add(new CloudSelect("classLetter", Map.of("A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H")))
                    .add(new SubmitButton("askForClassLetterSubmit", "Nastavimo!", "askForRating"))
            );
        } else {
            Collections.addAll(
                screens,
                new Screen("Welcome")
                    .add(new Header("logo.png", false))
                    .add(new Image("public/sovica.png", true))
                    .add(new Title("Želim čuti tvoje mišljenje o školi 😊"))
                    .add(new SubmitButton("welcomeStarted", "Idemo!", "askForRating"))
            );
        }

        Collections.addAll(
            screens,
            new Screen("askForRating")
                .add(new Header("logo.png", true)) 
                .add(new Title("Kolika je vjerojatnost da bi preporučio MIOC frendu ili frendici?"))
                .add(new Description("Označi odgovor na skali od 0 do 10. 0 znači da ne bi uopće preporučio, a 10 da bi sigurno preporučio."))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingStarted", "Idemo!", "askForFeedback")), //dodaj slider
            new Screen("askForFeedback")
                .add(new Header("logo.png", true))
                .add(new Title("Zašto?"))
                .add(new Description("Slobodno napiši zašto si se odlučio za tu ocjenu i što možemo učiniti da bi ona bila bolja."))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackSubmit", "Pošalji", "endScreen")),
            new Screen("endScreen")
                .add(new Image("public/srce.png", false))
                .add(new Title("Tvoj odgovor je poslan!"))
                .add(new Description("Hvala ti na odgovoru! Sada se možeš vratiti na WhatsApp."))
        );

        ArmoryAPI.show(
            getConnection("armory"),
            screens
        );
    }
}
