package agents;

import com.mindsmiths.ruleEngine.model.Agent;

import lombok.*;
import com.mindsmiths.ruleEngine.util.Log;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.Title;
import com.mindsmiths.armory.component.Description;
import com.mindsmiths.armory.component.SubmitButton;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.Input;
import com.mindsmiths.armory.component.TextArea;
import com.mindsmiths.armory.component.CustomComponent;

import java.io.IOException;
import java.util.List;
import com.mindsmiths.emailAdapter.NewEmail;
import com.mindsmiths.emailAdapter.EmailAdapterAPI;
import com.mindsmiths.gsheetsAdapter.GSheetsAdapterAPI;
import com.mindsmiths.gsheetsAdapter.reply.Spreadsheet;

import com.mindsmiths.gpt3.GPT3AdapterAPI;


@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Moli extends Agent {

    String email = "test@gmail.com";

    String gender;
    Integer age;
    Integer rating;
    String feedback;
    String response;

    public Moli(String email){
        this.id = email;
        setConnection("email", email);
    }

    public void askGPT3() {
        String intro = String.format("Write me a simple, one or two sentences long, response in Croatian where you thank a student for finishing the survey. Dont make it too formal.");
        simpleGPT3Request(intro);
    }

    public void simpleGPT3Request(String prompt) {
        Log.info("Prompt for GPT-3:\n" + prompt);
        GPT3AdapterAPI.complete(
            prompt, // input prompt
            "text-davinci-001", // model
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

    public String setResponse(String response) {
        return response;
    }

    public void showHelloScreen() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen("Welcome")
                .add(new Header("logo.png", false))
                .add(new Title("Bok, ja sam Moli! Može anketa?"))
                .add(new Description("Hvala ti puno što mi pomažeš <3"))
                .add(new SubmitButton("welcomeStarted", "Idemo!", "askForGender")),
            new Screen("askForGender")
                .add(new Header("logo.png", true)) 
                .add(new Title("Jesi li miočanin ili miočanka?")) 
                .add(new SubmitButton("askForGenderStartedm", "Miočanin", "askForAge")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForGenderStartedf", "Miočanka", "askForAge")),
            /*new Screen("askForAgem") //postoje m i f verzije stranica sa prilagodenim recenicama s obzirom na spol
                .add(new Header("logo.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new SubmitButton("askForAgemStarted", "Prvi", "askForRatingm")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgemStarted", "Drugi", "askForRatingm"))
                .add(new SubmitButton("askForAgemStarted", "Treći", "askForRatingm"))
                .add(new SubmitButton("askForAgemStarted", "Četvrti", "askForRatingm")),*/
            /*new Screen("askForAgef")
                .add(new Header("logo.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new SubmitButton("askForAgefStarted", "Prvi", "askForRatingf")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgefStarted", "Drugi", "askForRatingf"))
                .add(new SubmitButton("askForAgefStarted", "Treći", "askForRatingf"))
                .add(new SubmitButton("askForAgefStarted", "Četvrti", "askForRatingf")),*/
            new Screen("askForAge")
                .add(new Header("logo.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new SubmitButton("askForAgeStarted1", "Prvi", "askForRating")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgeStarted2", "Drugi", "askForRating"))
                .add(new SubmitButton("askForAgeStarted3", "Treći", "askForRating"))
                .add(new SubmitButton("askForAgeStarted4", "Četvrti", "askForRating")),
            /*new Screen("askForRatingm")
                .add(new Header("logo.png", true)) 
                .add(new Title("Preporučuješ li MIOC?"))
                .add(new Description("Kolika je vjerojatnost da bi preporučio MIOC frendu/frendici?"))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingmStarted", "Idemo!", "askForFeedbackm")), //dodaj slider
            new Screen("askForRatingf")
                .add(new Header("logo.png", true)) 
                .add(new Title("Preporučuješ li MIOC?"))
                .add(new Description("Kolika je vjerojatnost da bi preporučila MIOC frendu/frendici?"))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingfStarted", "Idemo!", "askForFeedbackf")), //dodaj slider*/
            new Screen("askForRating")
                .add(new Header("logo.png", true)) 
                .add(new Title("Preporučuješ li MIOC?"))
                .add(new Description("Kolika je vjerojatnost da bi preporučio MIOC frendu/frendici?"))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingStarted", "Idemo!", "askForFeedback")), //dodaj slider
            /*new Screen("askForFeedbackm")
                .add(new Header("logo.png", true))
                .add(new Title("Što bi moglo biti bolje?"))
                .add(new Description("Napiši koje promjene bi škola morala uvesti da bi joj dao veću ocjenu?"))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackmStarted", "Pošalji", "endScreen")),
            new Screen("askForFeedbackf")
                .add(new Header("logo.png", true))
                .add(new Title("Što bi moglo biti bolje?"))
                .add(new Description("Napiši koje promjene bi škola morala uvesti da bi joj dala veću ocjenu?"))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackfStarted", "Pošalji", "endScreen")),*/
            new Screen("askForFeedback")
                .add(new Header("logo.png", true))
                .add(new Title("Što bi moglo biti bolje?"))
                .add(new Description("Napiši koje promjene bi škola morala uvesti da bi joj dao veću ocjenu?"))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackStarted", "Pošalji", "endScreen")),
            new Screen("endScreen")
                .add(new Header("logo.png", false))
                .add(new Title("Tvoj odgovor je poslan!"))
                .add(new Description(response))
                //.add(new Description("Ovu poruku za tebe je generirao gpt3! :)"))


        );
        /*try {
            sendEmail(List.of("marko.zelenovicc@gmail.com"), "Test title", "Test 12345");
            Log.info("Mail uspjesno poslan!");
        } catch(IOException ex) {
            Log.info("Exception! " + ex.toString());
        }*/
    }

    public void sendEmail(List<String> recipients, String emailTitle, String emailText) throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(recipients);
        email.setSubject(emailTitle);
        email.setPlainText(emailText);

        EmailAdapterAPI.newEmail(email);
    }
}
