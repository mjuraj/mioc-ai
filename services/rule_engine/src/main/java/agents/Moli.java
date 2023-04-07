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
import com.mindsmiths.armory.component.TextArea;
import com.mindsmiths.armory.component.Image;
import com.mindsmiths.armory.component.Input;
import com.mindsmiths.armory.component.CustomComponent;
import com.mindsmiths.sdk.utils.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import com.mindsmiths.emailAdapter.NewEmail;
import com.mindsmiths.emailAdapter.EmailAdapterAPI;

import com.mindsmiths.gpt3.GPT3AdapterAPI;

import config.Settings;


@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Moli extends Agent {
    String gender;
    Integer age;
    Integer rating;
    String feedback;
    String response;

    public boolean reminderSent;
    public LocalDateTime lastSurveyEmailSent;

    public String getArmoryUrl() {
        return Settings.ARMORY_SITE_URL + "/" + getConnection("armory");
    }

    public String getIntroEmailText() {
        return Settings.getInstance().ONBOARDING_EMAIL_TEMPLATE.replace("%ARMORY_URL%", getArmoryUrl());
    }

    public Moli(String email){
        this.id = email;
        setConnection("email", email);
        setConnection("armory", Utils.randomString());
    }

    public void askGPT3() {
        String intro = String.format("Write me a simple, one or two sentences long, response that must be in Croatian language where you thank a student for finishing the survey. Dont make it too formal.");
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

    public void showOnboardingScreen() {
        ArmoryAPI.show(
            getConnection("armory"), 
            new Screen("onboardingHello")
                .add(new Header("logo.png", false))
                .add(new Image("public/sovica.png", true))
                .add(new Title("Ja sam Moli!"))
                .add(new Description("AI asistent po danu, špijun MIOC-a po noći :D"))
                .add(new Description("Želiš me upoznati? Pridruži mi se!"))
                .add(new SubmitButton("onboardingStarted", "Idemo!", "askForEmail")),
            new Screen("askForEmail")
                .add(new Header("logo.png", false))
                .add(new Title("Vau! Baš cool!"))
                .add(new Description("Drago mi je što želiš sudjelovati! Za početak, sve što trebaš je upisati svoj mail kako bih te mogla kontaktirati :)"))
                .add(new Input("email", "ivo.peric@gmail.com", "email", true))
                .add(new SubmitButton("emailSubmit", "Idemo!", "onboardingFinished")),
            new Screen("onboardingFinished")
                .add(new Header("logo.png", false))
                .add(new Image("public/sovica.png", true))
                .add(new Title("Hvala ti :D"))
                .add(new Description("Uskoro ćeš primiti mail. Hvala ti na pomoći!"))
        );
    }

    public void showHelloScreen() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen("Welcome")
                .add(new Header("logo.png", false))
                .add(new Title("Prvo kratko upoznavanje, a onda želim čuti tvoje mišljenje ;)"))
                .add(new Image("public/sovica.png", true))
                // .add(new Description("Hvala ti puno što mi pomažeš <3"))
                .add(new SubmitButton("welcomeStarted", "Idemo!", "askForGender")),
            new Screen("askForGender")
                .add(new Header("logo.png", true)) 
                .add(new Title("Jesi li miočanin ili miočanka?")) 
                .add(new Image("public/sovica.png", true))
                .add(new SubmitButton("askForGenderStartedm", "Miočanin", "askForAge")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForGenderStartedf", "Miočanka", "askForAge")),
            /*new Screen("askForAgem") //postoje m i f verzije stranica sa prilagodenim recenicama s obzirom na spol
                .add(new Header("sovica.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new SubmitButton("askForAgemStarted", "Prvi", "askForRatingm")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgemStarted", "Drugi", "askForRatingm"))
                .add(new SubmitButton("askForAgemStarted", "Treći", "askForRatingm"))
                .add(new SubmitButton("askForAgemStarted", "Četvrti", "askForRatingm")),*/
            /*new Screen("askForAgef")
                .add(new Header("sovica.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new SubmitButton("askForAgefStarted", "Prvi", "askForRatingf")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgefStarted", "Drugi", "askForRatingf"))
                .add(new SubmitButton("askForAgefStarted", "Treći", "askForRatingf"))
                .add(new SubmitButton("askForAgefStarted", "Četvrti", "askForRatingf")),*/
            new Screen("askForAge")
                .add(new Header("logo.png", true)) 
                .add(new Title("Koji si razred?"))
                .add(new Image("public/sovica.png", true))
                .add(new SubmitButton("askForAgeStarted1", "Prvi", "askForRating")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgeStarted2", "Drugi", "askForRating"))
                .add(new SubmitButton("askForAgeStarted3", "Treći", "askForRating"))
                .add(new SubmitButton("askForAgeStarted4", "Četvrti", "askForRating")),
            /*new Screen("askForRatingm")
                .add(new Header("sovica.png", true)) 
                .add(new Title("Preporučuješ li MIOC?"))
                .add(new Description("Kolika je vjerojatnost da bi preporučio MIOC frendu/frendici?"))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingmStarted", "Idemo!", "askForFeedbackm")), //dodaj slider
            new Screen("askForRatingf")
                .add(new Header("sovica.png", true)) 
                .add(new Title("Preporučuješ li MIOC?"))
                .add(new Description("Kolika je vjerojatnost da bi preporučila MIOC frendu/frendici?"))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingfStarted", "Idemo!", "askForFeedbackf")), //dodaj slider*/
            new Screen("askForRating")
                .add(new Header("logo.png", true)) 
                .add(new Title("Kolika je vjerojatnost da bi preporučio MIOC frendu ili frendici?"))
                .add(new Description("Označi odgovor na skali od 0 do 10. 0 znači da ne bi uopće preporučio, a 10 da bi sigurno preporučio."))
                .add(new CustomComponent("Slider").addParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingStarted", "Idemo!", "askForFeedback")), //dodaj slider
            /*new Screen("askForFeedbackm")
                .add(new Header("sovica.png", true))
                .add(new Title("Što bi moglo biti bolje?"))
                .add(new Description("Napiši koje promjene bi škola morala uvesti da bi joj dao veću ocjenu?"))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackmStarted", "Pošalji", "endScreen")),
            new Screen("askForFeedbackf")
                .add(new Header("sovica.png", true))
                .add(new Title("Što bi moglo biti bolje?"))
                .add(new Description("Napiši koje promjene bi škola morala uvesti da bi joj dala veću ocjenu?"))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackfStarted", "Pošalji", "endScreen")),*/
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

    public void sendEmail(List<String> recipients, String emailTitle, String emailText) throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(recipients);
        email.setSubject(emailTitle);
        email.setHtmlText(emailText);

        EmailAdapterAPI.newEmail(email);
    }
}
