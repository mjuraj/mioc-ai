package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import lombok.*;
import com.mindsmiths.ruleEngine.util.Log;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;

import com.mindsmiths.armory.component.Title;

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
            new Screen ("Hello")
                .add(new Title("Hello world!"))
        );
        try {
            sendEmail(List.of("sveba.vasic@gmail.com"), "Test title", "Test 1234");
            Log.info("Mail uspjesno poslan!");
        } catch(IOException ex) {
            Log.info("Exception! " + ex.toString());
        }
    }

    public void sendEmail(List<String> recipients, String emailTitle, String emailText) throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(recipients);
        email.setSubject(emailTitle);
        email.setPlainText(emailText);

        EmailAdapterAPI.newEmail(email);
    }
}