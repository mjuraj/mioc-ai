package agents;

import com.mindsmiths.ruleEngine.model.Agent;

import java.io.IOException;
import java.util.List;
import com.mindsmiths.emailAdapter.NewEmail;
import com.mindsmiths.emailAdapter.EmailAdapterAPI;

import java.time.LocalDateTime;

public class Principal extends Agent {
    public static String ID = "PRINCIPAL";

    public Principal() {
        id = ID;
    }

    public LocalDateTime lastEmailSentTime;

    public void sendEmail(List<String> recipients, String emailTitle, String emailText) throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(recipients);
        email.setSubject(emailTitle);
        email.setPlainText(emailText);

        EmailAdapterAPI.newEmail(email);
    }
}
