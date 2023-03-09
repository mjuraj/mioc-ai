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

import java.util.List;
import com.mindsmiths.gsheetsAdapter.GSheetsAdapterAPI;
import com.mindsmiths.gsheetsAdapter.reply.Spreadsheet;


@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Smith extends Agent {

    LocalDateTime lastTableUpdate = null;
    int sheetSize = 0;
    int nextAnsRow = 0;

    // Agent id
    public Smith() {
        this.id = "SMITH";
    }

    public void processSheet(Spreadsheet spreadsheet) {
        Log.info(spreadsheet);
        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");
        sheetSize = answers.size();
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

    public void addReviewToSheet(String mail, String gender, Integer age, Integer rating, String feedback) {
        String range = String.format("Odgovori!A%d:E%d", sheetSize+2, sheetSize+2);
        List<List<String>> values = List.of(List.of(mail, gender, String.valueOf(age), String.valueOf(rating), feedback));
        GSheetsAdapterAPI.updateSheet(values, range);
    }
}
