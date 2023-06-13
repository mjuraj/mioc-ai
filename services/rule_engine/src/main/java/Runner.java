import agents.HITL;
import agents.Principal;
import agents.Smith;
import config.Settings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Log;


public class Runner extends RuleEngineService {


    public String readFileAsString(String filePath) {
        InputStream is = getClass().getResourceAsStream(filePath);
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            Log.error("Failed to read file: " + e.toString());
        }
        return "";
    }

    @Override
    public void initialize() {
        configureSignals(
            // TODO: listen to signals here
        );
        configureSignals(getClass().getResourceAsStream("config/signals.yaml"));
        
        Log.info("Principal email: " + Settings.PRINCIPAL_EMAIL);

        Settings.getInstance().ONBOARDING_EMAIL_TEMPLATE = readFileAsString("templates/onboarding_email.html");
        Settings.getInstance().NPS_REMINDER_EMAIL_TEMPLATE = readFileAsString("templates/nps_reminder.html");
        Settings.getInstance().PRINCIPAL_SUMMARY_EMAIL_TEMPLATE = readFileAsString("templates/principal_summary.html");
        Settings.getInstance().PRINCIPAL_SUMMARY_NO_ENTRIES_EMAIL_TEMPLATE = readFileAsString("templates/principal_summary_no_new_entries.html");

        if (!Agents.exists("SMITH"))
            Agents.createAgent(new Smith());

        if (!Agents.exists("PRINCIPAL"))
            Agents.createAgent(new Principal());

        if (!Agents.exists(HITL.ID))
            Agents.createAgent(new HITL());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
