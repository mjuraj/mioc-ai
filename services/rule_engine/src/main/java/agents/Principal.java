package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;

import config.Settings;
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
    public String averageScore = "";
    public int numberOfEntries = 0;

    public void requestTextSummary(SummaryReadyMessage message) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        averageScore = formatter.format(message.getTotalAverage());
        numberOfEntries = message.getNumberOfEntries();

        if (numberOfEntries == 0) {
            try {
                sendNoEntriesEmail();
            } catch (IOException e) {
                Log.error(e);
            }
            return;
        }

        String prompt = "Write in formal Croatian, ignore NSFW messages. Write 5 points (up to 10 words per point) in Croatian from the following statements:\n\n"
            + message.getFeedback();
    
        Log.info("GPT3 prompt for Principal mail: " + prompt);

        GPT3AdapterAPI.complete(
            prompt, // input prompt
            "text-davinci-003", // model
            3000, // max tokens
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

    public void sendNoEntriesEmail() throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(List.of(Settings.PRINCIPAL_EMAIL));
        email.setSubject("Moli - Novi odgovori učenika");

        String template = Settings.getInstance().PRINCIPAL_SUMMARY_NO_ENTRIES_EMAIL_TEMPLATE;
        template = template
            .replaceAll("%SPREADSHEET_URL%", "https://docs.google.com/spreadsheets/d/1Uy6qZWmCqIWt9upKf0yf7VYVTZ6pPFLma_Jn8Dc7NJY/edit?usp=sharing");

        email.setHtmlText(template);
        EmailAdapterAPI.newEmail(email);
    }

    public void sendEmail(String summary) throws IOException {
        NewEmail email = new NewEmail();
        email.setRecipients(List.of(Settings.PRINCIPAL_EMAIL));
        email.setSubject("Moli - Novi odgovori učenika");

        String template = Settings.getInstance().PRINCIPAL_SUMMARY_EMAIL_TEMPLATE;
        template = template
            .replaceAll("%SPREADSHEET_URL%", "https://docs.google.com/spreadsheets/d/1Uy6qZWmCqIWt9upKf0yf7VYVTZ6pPFLma_Jn8Dc7NJY/edit?usp=sharing")
            .replaceAll("%FEEDBACK_SUMMARY%", summary)
            .replaceAll("%NUMBER_OF_ENTRIES%", String.valueOf(numberOfEntries))
            .replaceAll("%AVERAGE_SCORE%", averageScore);

        email.setHtmlText(template);
        EmailAdapterAPI.newEmail(email);
    }
}
