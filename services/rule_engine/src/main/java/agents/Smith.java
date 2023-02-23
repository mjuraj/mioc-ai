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
    int nextRow = 0;
    int odgNext = 0;
    Spreadsheet sheet;

    // Agent id
    public Smith() {
        this.id = "SMITH";
    }

    // Getting the sheet as an object and getting the first free row in the spreadsheet
    public void processSheet(Spreadsheet spreadsheet){
        sheet = spreadsheet;
        List<Map<String, String>> sheetData = spreadsheet.getSheets().get("Mailovi");
        List<Map<String, String>> odg = spreadsheet.getSheets().get("Odgovori");
        nextRow = sheetData.size();
        odgNext = odg.size();
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
    public void getReview(String mail, String ocjena, String review){
        String range = String.format("Odgovori!A%d:C%d",odgNext+2,odgNext+2);
        List<List<String>> values = List.of(List.of(mail,ocjena,review));
        GSheetsAdapterAPI.updateSheet(values, range);
    }

    // Going through the list of mails in the spreadsheet and creating agents for mails who don't already have one
    public void newAgent(){
        List<Map<String, String>> popis = sheet.getSheets().get("Mailovi");

        for (Map<String, String> item : popis) {
            for (String key : item.keySet()) {
                String email = item.get(key);
                if(!Agents.exists(email)){
                    Agents.createAgent(new Moli(email));
                }
            }
        }
    }
}
