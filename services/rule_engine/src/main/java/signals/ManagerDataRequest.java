package signals;

import com.mindsmiths.dashboard.models.Manager;
import com.mindsmiths.sdk.core.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDataRequest extends Message {
    private Manager manager;
    private String categoryId;
    private String schedulingAgentId;
}