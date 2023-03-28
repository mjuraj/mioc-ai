package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;

import lombok.Data;
import lombok.ToString;
import signals.SummaryReadyMessage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import com.mindsmiths.emailAdapter.NewEmail;
import com.mindsmiths.gpt3.GPT3AdapterAPI;
import com.mindsmiths.emailAdapter.EmailAdapterAPI;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
public class Principal extends Agent {
    public static String ID = "PRINCIPAL";

    public Principal() {
        id = ID;
    }

    public LocalDateTime lastEmailSentTime;
    public boolean textSummaryBuilding = false;

    public void requestTextSummary(SummaryReadyMessage message) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        String prompt = "You are Moli, an AI agent for schools." +
            "Write a formal email introduction for 'ravnatelju' in Croatian and introduce yourself. " +
            "Write a short passage that you have new responses from the last feedback round. " +
            "Then summarize the following messages: '\n\n" + 
            "Učenik koji bi vrlo rado preporučio školu prijateljima smatra:\n" +
            message.getBestFeedback() + 
            "\n\nUčenik koji ne bi preporučio školu prijateljima smatra:\n" +
            message.getWorstFeedback() + 
            "\nProsječna ocjena je " + formatter.format(message.getTotalAverage()) + ".'\n\nAdd a link to spreadsheet for more information 'https://docs.google.com/spreadsheets/d/1Uy6qZWmCqIWt9upKf0yf7VYVTZ6pPFLma_Jn8Dc7NJY/edit?usp=sharing'. Write a formal ending in Croatian.";
    
        Log.info("GPT3 prompt for Principal mail: " + prompt);

        GPT3AdapterAPI.complete(
            prompt, // input prompt
            "text-davinci-003", // model
            500, // max tokens
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

    public void sendEmail(String emailTitle, String emailText) throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(List.of("domagoj.gavranic@gmail.com"));
        email.setSubject(emailTitle);
        email.setPlainText(emailText);

        EmailAdapterAPI.newEmail(email);
    }
}
