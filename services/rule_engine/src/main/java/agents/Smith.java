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
    int nextMailRow = 0;
    int nextAnsRow = 0;
    Spreadsheet sheet;

    // Agent id
    public Smith() {
        this.id = "SMITH";
    }

    // Getting the sheet as an object and getting the first free row in the spreadsheet
    public void processSpreadsheet(Spreadsheet spreadsheet){
        sheet = spreadsheet;
        List<Map<String, String>> emails = spreadsheet.getSheets().get("Mailovi");
        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");
        nextMailRow = emails.size() + 2;
        nextAnsRow = answers.size() + 2;
    }

    /* 

    OPTIONAL - Function which updates the "Mailovi" sheet with a mock mail for now

    public void updateGSheet() {
        List<List<String>> values = List.of(List.of("jedan@mail.com"));
        String range = String.format("Mailovi!A%d",nextRow+2);
        GSheetsAdapterAPI.updateSheet(values, range);
    }

    */

    // Function to update "Odgovori" sheet with given parameters
    // TODO: set the function up to work with data collected from armory
    public void getReview(String mail, String grade, String review){
        String range = String.format("Odgovori!A%d:C%d", nextAnsRow, nextAnsRow);
        List<List<String>> values = List.of(List.of(mail,grade,review));
        GSheetsAdapterAPI.updateSheet(values, range);
    }

    // Going through the list of mails in the spreadsheet and creating agents for mails who don't already have one
    public void createNewAgents(){
        List<Map<String, String>> mailList = sheet.getSheets().get("Mailovi");

        for (Map<String, String> item : mailList) {
            for (String email: item.values()) {
                if(!Agents.exists(email)){
                    Agents.createAgent(new Moli(email));
                }
            }
        }
    }
}
