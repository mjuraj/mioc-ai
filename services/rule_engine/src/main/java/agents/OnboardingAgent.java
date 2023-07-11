package agents;

import java.util.Map;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.CloudSelect;
import com.mindsmiths.armory.component.CustomComponent;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.Image;
import com.mindsmiths.armory.component.Input;
import com.mindsmiths.armory.component.Title;
import com.mindsmiths.armory.component.SubmitButton;
import com.mindsmiths.armory.component.Description;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;

import lombok.*;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class OnboardingAgent extends Agent {

    String phoneNumber;
    String name;
    String gender;
    Integer age; 
    String classLetter;

    public OnboardingAgent(String connectionId) {
        setConnection("armory", connectionId);
    }

    public void showOnboardingFlow() {
        Log.info("Triggered onboarding flow on " + getConnection("armory"));
        ArmoryAPI.show(
            getConnection("armory"),
                new Screen("onboardingHello")
                    .add(new CustomComponent("StartScreenGraphics"))
                    .add(new SubmitButton("onboardingStarted", "Idemo!", "askForWapp")),
                new Screen("askForWapp")
                    .add(new Header("mioc.png", true))
                    .add(new Title("Upoznajmo se!"))
                    .add(new Image("public/sovica.png"))
                    .add(new Description("Upiši svoj broj u kućicu i postani dio moje ekipe 😉"))
                    .add(new Input("telephone", "+3850012345678", "tel", true))
                    .add(new SubmitButton("numberSubmit", "Pošalji!", "askForName")),
                new Screen("askForName")
                    .add(new Header("logo.png", false))
                    .add(new Image("public/sovica.png"))
                    .add(new Title("Koje je tvoje ime?"))
                    .add(new Input("name", "Moli", true))
                    .add(new SubmitButton("askForNameFinished", "Nastavimo!", "askForGender")),
                new Screen("askForGender")
                    .add(new Header("logo.png", true)) 
                    .add(new Title("Jesi li miočanin ili miočanka?")) 
                    .add(new Image("public/sovica.png"))
                    .add(new SubmitButton("askForGenderStartedm", "Miočanin", "askForAge")) //uhvati podatke i salji Ravnatelj agentu
                    .add(new SubmitButton("askForGenderStartedf", "Miočanka", "askForAge")),
                new Screen("askForAge")
                    .add(new Header("logo.png", true)) 
                    .add(new Title("Koji si razred?"))
                    .add(new Image("public/sovica.png"))
                    .add(new SubmitButton("askForAgeStarted1", "Prvi", "askForClassLetter")) //uhvati podatke i salji Ravnatelj agentu
                    .add(new SubmitButton("askForAgeStarted2", "Drugi", "askForClassLetter"))
                    .add(new SubmitButton("askForAgeStarted3", "Treći", "askForClassLetter"))
                    .add(new SubmitButton("askForAgeStarted4", "Četvrti", "askForClassLetter")),
                new Screen("askForClassLetter")
                    .add(new Header("logo.png", true)) 
                    .add(new Title("Koje slovo?"))
                    .add(new Description("Odaberi samo jedno slovo."))
                    .add(new Image("public/sovica.png"))
                    .add(new CloudSelect("classLetter", Map.of("A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H")))
                    .add(new SubmitButton("askForClassLetterSubmit", "Nastavimo!", "onboardingFinished")),
                new Screen("onboardingFinished")
                    .add(new CustomComponent("EndScreenGraphics"))
                    .add(new Image("public/moli_heart.png"))
                    .add(new Title("Hvala ti!"))
                    .add(new Description("Sad kad smo probili led, spremni smo da učinimo MIOC baš po tvojoj mjeri! 🤩"))
            );
    }
    
    
}
