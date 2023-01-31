package agents;

import com.mindsmiths.ruleEngine.model.Agent;
import lombok.*;

import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;

import com.mindsmiths.armory.component.Title;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Moli extends Agent {
    public void showHelloScreen() {
        ArmoryAPI.show(
            getConnection("armory"),
            new Screen ("Hello")
                .add(new Title("Hello world!"))
        );
    }
}