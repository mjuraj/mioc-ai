package agents;

import com.mindsmiths.dashboard.models.Client;
import com.mindsmiths.dashboard.models.Manager;
import com.mindsmiths.sdk.core.db.Database;
import com.mindsmiths.sdk.utils.Utils;
import com.mindsmiths.dashboard.models.AssistantConfiguration;
import com.mindsmiths.dashboard.DashboardAPI;
import com.mongodb.client.model.Filters;

import hitl.HITLPlugin;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAgent extends ChatAgent {
    Client client;
    Manager manager;
    boolean waitingForManager;

    Integer age;
    String gender;
    String classLetter;

    public ClientAgent(String name, String phoneNumber, Integer age, String gender, String classLetter) {
        this.age = age;
        this.gender = gender;
        this.classLetter = classLetter;
        this.client = new Client(name, phoneNumber);
        
        setConnection("phone", client.getPhoneNumber());
    }

    public void clientUpdate(Client client) {
        this.client = client;
        timeZone = client.getTimeZone();

        // Update connections
        if (getConnection("armory") == null)
            setConnection("armory", Utils.randomString());

        setConnection("dashboardId", client.getId());
        if (client.getEmail() != null) setConnection("email", client.getEmail());
        if (client.getAssistantConfigurationId() != null)
            setConnection("assistant", client.getAssistantConfigurationId());

        // Update assistant configuration
        if (assistantConfiguration == null || !assistantConfiguration.getConfigId().equals(client.getAssistantConfigurationId())) {
            this.reset();
            assistantConfiguration = Database.get(Filters.eq("configId", client.getAssistantConfigurationId()), AssistantConfiguration.class);
        }

        // Update manager
        if (client.getManagerId() == null)
            connectToAssistantOwner();
        if (client.getManagerId() != null && manager != null && !manager.getId().equals(client.getManagerId()))
            HITLPlugin.deleteChannel();
    }

    public void connectToAssistantOwner() {
        client.setManagerId(assistantConfiguration.getDefaultManagerId());
        DashboardAPI.updateOrCreateClient(client);
    }

    public String getName() {
        return (client.getFirstName() + " " + client.getLastName()).trim();
    }

    @Override
    public Map<String, Object> fillContext() {
        Map<String, Object> context = super.fillContext();

        if (client != null) {
            context.put("name", getName());
            context.put("firstName", client.getFirstName());
            context.put("lastName", client.getLastName());
            context.put("email", client.getEmail());
            context.put("phone", client.getPhoneNumber());
            context.put("age", age);
            context.put("gender", gender);
        }

        if (manager != null) {
            context.put("managerFirstName", manager.getFirstName());
            context.put("managerLastName", manager.getLastName());
            context.put("managerEmail", manager.getEmail());
        }

        return context;
    }

    @Override
    public void switchAgent(AssistantConfiguration config) {
        super.switchAgent(config);
        client.setAssistantConfigurationId(config.getConfigId());
        DashboardAPI.updateOrCreateClient(client);
    }
}
