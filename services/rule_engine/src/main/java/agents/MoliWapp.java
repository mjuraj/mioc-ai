package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import lombok.*;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class MoliWapp extends Agent {

    public MoliWapp(String phone) {
        this.id = phone;
        //setConnection("", phone);
    } 
}
