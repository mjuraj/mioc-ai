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

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Moli extends Agent {
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
                .add(new SubmitButton("askForGenderStarted", "Miočanin", "askForAge")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForGenderStarted", "Miočanka", "askForAge")),
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
                .add(new SubmitButton("askForAgeStarted", "Prvi", "askForRating")) //uhvati podatke i salji Ravnatelj agentu
                .add(new SubmitButton("askForAgeStarted", "Drugi", "askForRating"))
                .add(new SubmitButton("askForAgeStarted", "Treći", "askForRating"))
                .add(new SubmitButton("askForAgeStarted", "Četvrti", "askForRating")),
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
                .add(new Title("Hvala ti! I tvoje mišljenje je bitno."))
                .add(new Description("Zajedno ćemo učiniti MIOC boljim <3"))


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