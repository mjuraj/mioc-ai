package agents;

import com.mindsmiths.dashboard.models.Client;
import com.mindsmiths.dashboard.models.Manager;
import com.mindsmiths.sdk.core.db.Database;
import com.mindsmiths.sdk.utils.Utils;
import com.mindsmiths.dashboard.models.AssistantConfiguration;
import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.Image;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.CustomComponent;
import com.mindsmiths.armory.component.Description;
import com.mindsmiths.armory.component.Title;
import com.mindsmiths.armory.component.SubmitButton;
import com.mindsmiths.armory.component.TextArea;
import com.mindsmiths.dashboard.DashboardAPI;
import com.mongodb.client.model.Filters;

import hitl.HITLPlugin;
import lombok.*;

import java.time.LocalDateTime;
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

    Integer rating;
    String feedback;

    boolean npsReminderSent = false;
    LocalDateTime lastNpsSent = null;
    boolean introducedToClient = false;

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

    public void showNPSFlow() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen("Welcome")
                .add(new Header("logo.png", false))
                .add(new Image("public/sovica.png"))
                .add(new Title("Želim čuti tvoje mišljenje o školi 😊"))
                .add(new SubmitButton("welcomeStarted", "Idemo!", "askForRating")),
            new Screen("askForRating")
                .add(new Header("logo.png")) 
                .add(new Title("Kolika je vjerojatnost da bi preporučio MIOC frendu ili frendici?"))
                .add(new Description("Označi odgovor na skali od 0 do 10. 0 znači da ne bi uopće preporučio, a 10 da bi sigurno preporučio."))
                .add(new CustomComponent("Slider").setParam("inputId", "nps"))
                .add(new SubmitButton("askForRatingStarted", "Idemo!", "askForFeedback")), //dodaj slider
            new Screen("askForFeedback")
                .add(new Header("logo.png", true))
                .add(new Title("Zašto?"))
                .add(new Description("Slobodno napiši zašto si se odlučio za tu ocjenu i što možemo učiniti da bi ona bila bolja."))
                .add(new TextArea("feedback", "Napiši svoj kometar..."))
                .add(new SubmitButton("askForFeedbackSubmit", "Pošalji", "endScreen")),
            new Screen("endScreen")
                .add(new Image("public/srce.png"))
                .add(new Title("Tvoj odgovor je poslan!"))
                .add(new Description("Hvala ti na odgovoru! Sada se možeš vratiti na WhatsApp."))
        );
    }

    @Override
    public Map<String, Object> fillContext() {
        Map<String, Object> context = super.fillContext();

        context.put("npsUrl", getArmoryUrl("nps"));

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
