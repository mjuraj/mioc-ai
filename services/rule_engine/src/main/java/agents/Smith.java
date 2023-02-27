package agents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mindsmiths.gsheetsAdapter.GSheetsAdapterAPI;
import com.mindsmiths.gsheetsAdapter.reply.Spreadsheet;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Agents;
import com.mindsmiths.ruleEngine.util.Log;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Smith extends Agent {

    LocalDateTime lastTableUpdate = null;
    int nextAnsRow = 0;

    // Agent id
    public Smith() {
        this.id = "SMITH";
    }

    // Getting the sheet as an object and getting the first free row in the spreadsheet
    public void processSpreadsheet(Spreadsheet spreadsheet){
        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");
        nextAnsRow = answers.size() + 2;

        createNewAgents(spreadsheet);
    }

    // Going through the list of mails in the spreadsheet and creating agents for mails who don't already have one
    public void createNewAgents(Spreadsheet spreadsheet){
        List<Map<String, String>> mailList = spreadsheet.getSheets().get("Mailovi");

        for (Map<String, String> item : mailList) {
            for (String key : item.keySet()) {
                String email = item.get(key);
                if(!Agents.exists(email)){
                    Agents.createAgent(new Moli(email));
                }
            }
        }
    }
}
