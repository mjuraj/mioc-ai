import agents.ClientAgent;
import agents.HITL;
import agents.ManagerAgent;
import agents.OnboardingAgent;
import agents.Principal;
import agents.Smith;
import config.Settings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import com.mindsmiths.armory.event.UserConnected;
import com.mindsmiths.controlPanel.events.Configuration;
import com.mindsmiths.dashboard.models.AssistantConfiguration;
import com.mindsmiths.dashboard.models.Client;
import com.mindsmiths.dashboard.models.Manager;
import com.mindsmiths.dashboard.models.PersonInterface;
import com.mindsmiths.discordAdapter.message.DiscordButtonPressed;
import com.mindsmiths.mitems.Flow;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.subscriptions.DataChanges;
import com.mindsmiths.ruleEngine.subscriptions.Events;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Log;
import com.mindsmiths.sdk.core.api.DataChangeType;


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

    public List<String> getAgentByIdOrPhone(Class<? extends Agent> agentCls, PersonInterface person, DataChangeType changeType, String phoneNumber) {
        List<Agent> agents = Agents.getByConnection("dashboardId", person.getId());
        if (changeType == DataChangeType.DELETED) {
            agents.forEach(a -> {
                Agents.deleteAgent(a, false);
                if (a instanceof ManagerAgent)
                    Agents.deleteAgent(((ManagerAgent) a).getSchedulingAgentId());
            });
            return List.of();
        }
        if (agents.isEmpty()) {
            agents = Agents.getByConnection("phone", phoneNumber);
            if (agents.isEmpty()) {
                try {
                    agents = Agents.getOrCreateByConnection("dashboardId", person.getId(), agentCls.getDeclaredConstructor().newInstance());
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            List<String> agentIds = agents.stream().map(Agent::getId).toList();
            Agents.getByConnection("phone", phoneNumber).stream()
                    .filter(a -> !agentIds.contains(a.getId())).forEach(a -> Agents.deleteAgent(a, false));
        }
        return agents.stream().map(Agent::getId).toList();
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

        configureSignals(Events.on(DiscordButtonPressed.class).sendTo("HITL"));
        configureSignals(
                DataChanges.on(Client.class).sendTo(
                        (c, ct) -> getAgentByIdOrPhone(ClientAgent.class, c, ct, c.getPhoneNumber())
                ),
                DataChanges.on(Manager.class).sendTo(
                        (m, ct) -> getAgentByIdOrPhone(ManagerAgent.class, m, ct, m.getPhoneNumber())
                ),
                DataChanges.on(AssistantConfiguration.class).sendTo(
                        (assistantConfiguration, changeType) -> Agents.getByConnection("assistant", assistantConfiguration.getConfigId()))
        );
        configureSignals(
            Events.on(UserConnected.class).sendTo(
                (msg) -> msg.getParamAsString("f") != null && msg.getParamAsString("f").equalsIgnoreCase("onboarding") 
                ? Agents.getOrCreateByConnection("armory", msg.getConnectionId(), new OnboardingAgent(msg.getConnectionId()))
                : Agents.getByConnection("armory", msg.getConnectionId()))
        );
        registerForChanges(AssistantConfiguration.class);
        registerForChanges(Manager.class);
        registerForChanges(Flow.class);
        registerForChanges(Configuration.class);

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
