package agents;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.Header;
import com.mindsmiths.armory.component.Title;
import com.mindsmiths.calendarAdapter.api.Day;
import com.mindsmiths.calendarAdapter.api.EmailProvider;
import com.mindsmiths.calendarAdapter.api.OpenHours;
import com.mindsmiths.calendarAdapter.requests.GetAuthUrl;
import com.mindsmiths.dashboard.DashboardAPI;
import com.mindsmiths.dashboard.models.AssistantConfiguration;
import com.mindsmiths.dashboard.models.Manager;
import com.mindsmiths.mitems.Mitems;
import com.mindsmiths.scheduling.models.ScheduleConfiguration;
import com.mindsmiths.scheduling.signals.SetScheduleConfiguration;
import com.mindsmiths.sdk.core.db.Database;
import com.mindsmiths.sdk.utils.Utils;
import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import signals.ManagerDataRequest;
import utils.Settings;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class ManagerAgent extends ChatAgent {
    Manager manager;

    String accessToken;
    boolean pendingAuth;
    String timeZone = Settings.DEFAULT_TIME_ZONE;

    public Set<String> clients = new HashSet<>();
    String categoryId;
    boolean pendingCategory;

    public void managerUpdate(Manager manager) {
        this.manager = manager;

        setConnection("phone", manager.getPhoneNumber());
        setConnection("dashboardId", manager.getId());
        if (getConnection("armory") == null)
            setConnection("armory", Utils.randomString());
        if (manager.getAssistantConfigurationId() != null)
            setConnection("assistant", manager.getAssistantConfigurationId());

        // Update assistant configuration
        if (assistantConfiguration == null || !assistantConfiguration.getConfigId().equals(manager.getAssistantConfigurationId())) {
            this.reset();
            assistantConfiguration = Database.get(Filters.eq("configId", manager.getAssistantConfigurationId()), AssistantConfiguration.class);
        }

        timeZone = manager.getTimeZone();
        if (schedulingAgentId != null) {
            send(schedulingAgentId, new SetScheduleConfiguration(
                getScheduleConfiguration(manager.getFirstName(), manager.getEmail())));
        }
        for (String client : clients)
            send(client, new ManagerDataRequest(manager, categoryId, schedulingAgentId));
    }

    @Override
    public Map<String, Object> fillContext() {
        Map<String, Object> context = super.fillContext();
        context.put("name", manager.getFirstName() + " " + manager.getLastName());
        context.put("email", manager.getEmail());
        context.put("description", manager.getDescription());
        return context;
    }

    public ScheduleConfiguration getScheduleConfiguration(String name, String email) {
        return
            new ScheduleConfiguration(
                "Meeting with " + name + " / {{name}}",
                15,
                30,
                null,
                null,
                List.of(new OpenHours(
                    List.of(email),
                    List.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY),
                    timeZone,
                    LocalTime.of(9, 0),
                    LocalTime.of(17, 0)
                )),
                7,
                120,
                0
            );
    }

    public void showAuthSuccessScreen() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen("auth-success")
                .setTemplate("CenteredContent")
                .add(new Header(Mitems.getText("manager.scheduling.logo"), false))
                .add(new Title(Mitems.getText("manager.scheduling.title"))));
    }

    public void askForAuthUrl(String email, String provider) {
        send(schedulingAgentId, new GetAuthUrl(
            Settings.ARMORY_SITE_URL + "/" + getConnection("armory"),
            email, EmailProvider.valueOf(provider.toLowerCase()), null));
    }

    @Override
    public void switchAgent(AssistantConfiguration config) {
        super.switchAgent(config);
        manager.setAssistantConfigurationId(config.getConfigId());
        DashboardAPI.updateOrCreateManager(manager);
    }

}
