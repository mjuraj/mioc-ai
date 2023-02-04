package agents;

import java.time.LocalDateTime;

import com.mindsmiths.gsheetsAdapter.reply.Spreadsheet;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Smith extends Agent {

    LocalDateTime lastTableUpdate = null;

    public Smith() {
        this.id = "SMITH";
    }

    public void processSheet(Spreadsheet spreadsheet){
        Log.info(spreadsheet);
    }
}
