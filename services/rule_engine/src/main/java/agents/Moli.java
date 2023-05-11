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

    public String getReminderEmailText() {
        return Settings.getInstance().NPS_REMINDER_EMAIL_TEMPLATE.replace("%ARMORY_URL%", getArmoryUrl());
    }

    public Moli(String email) {
        this.id = email;
        setConnection("email", email);
        setConnection("armory", Utils.randomString());
    }

    public void askGPT3() {
        String intro = String.format("You are Moli. Write me a simple, one or two sentences long, response that must be in Croatian language where you thank a highschool student for finishing the survey. Make it very friendly.");
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
                .add(new CustomComponent("StartScreenGraphics"))
                .add(new SubmitButton("onboardingStarted", "Idemo!", "askForWapp")),
            new Screen("askForWapp")
                .add(new Header("mioc.png", true))
                .add(new Title("Upoznajmo se!"))
                .add(new Image("public/sovica.png", true))
                .add(new Description("Upiši svoj broj u kućicu i postani dio moje ekipe 😉"))
                .add(new Input("telephone", "+3850012345678", "tel", true))
                .add(new SubmitButton("numberSubmit", "Pošalji!", "onboardingFinished")),
            new Screen("onboardingFinished")
                .add(new CustomComponent("EndScreenGraphics"))
                .add(new Image("public/moli_heart.png", true))
                .add(new Title("Hvala ti!"))
                .add(new Description("Sad kad smo probili led, spremni smo da učinimo MIOC baš po tvojoj mjeri! 🤩"))
        );
    }

    public void showHelloScreen() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen("Welcome")
                .add(new Header("logo.png", false))
                .add(new Image("public/sovica.png", true))
                .add(new Title("Sada sam dostupna i na WhatsApp-u ;)"))
                .add(new SubmitButton("welcomeStarted", "Reci mi više!", "askForWapp")),
            new Screen("askForWapp")
                .add(new Header("logo.png", true)) 
                .add(new Title("Pričajmo na WhatsApp-u!"))
                .add(new Description("Zaboravi mail, sada me možeš kontaktirati preko WhatsApp-a. Sve ostalo ostaje isto ;) <br /> Samo trebaš upisati svoj broj mobitela!"))
                .add(new Input("telephone", "+3850012345678", "tel", true))
                .add(new SubmitButton("numberSubmit", "Idemo!", "endScreen")),
                new Screen("endScreen")
                .add(new Image("public/srce.png", false))
                .add(new Title("Hvala ti!"))
                .add(new Description("Čujemo se na Wapp-u "))
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
