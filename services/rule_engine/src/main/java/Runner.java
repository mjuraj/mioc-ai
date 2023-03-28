import agents.Principal;
import agents.Smith;
import com.mindsmiths.ruleEngine.runner.RuleEngineService;
import com.mindsmiths.ruleEngine.util.Agents;


public class Runner extends RuleEngineService {
    @Override
    public void initialize() {
        configureSignals(
            // TODO: listen to signals here
        );
        configureSignals(getClass().getResourceAsStream("config/signals.yaml"));

        if (!Agents.exists("SMITH"))
            Agents.createAgent(new Smith());

        if (!Agents.exists("PRINCIPAL"))
            Agents.createAgent(new Principal());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
    }
}
