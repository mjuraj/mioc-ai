
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
import signals.SummaryReadyMessage;


@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Smith extends Agent {

    LocalDateTime lastTableUpdate = null;
    List<List<String>> sviOdgovori = new ArrayList<List<String>>();
    int sheetSize = 0;
    int nextAnsRow = sviOdgovori.size();

    boolean summaryRequested = false;
    
    // Agent id
    public Smith() {
        this.id = "SMITH";
    }

    public void processSpreadsheet(Spreadsheet spreadsheet) {
        Log.info(spreadsheet);
        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");

        sheetSize = answers.size();
        nextAnsRow += answers.size() + 2;

        createNewAgents(spreadsheet);
    }

    public void generateSummaryFromData(Spreadsheet spreadsheet) {


        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");

        int totalSum = 0;
        int count = 0;

        int bestScoreIndex = 0;
        int bestScore = 0;
        int worstScoreIndex = 0;
        int worstScore = 11;

        for (Map<String, String> item : answers) {
            
            int rating = Integer.parseInt(item.get("Rating"));

            if (rating > bestScore) {
                bestScoreIndex = count;
                bestScore = rating;
            } 
            if (rating < worstScore) {
                worstScore = rating;
                worstScoreIndex = count;
            }

            totalSum += rating;
            count++;
        }

        String bestFeedback = answers.get(bestScoreIndex).get("Feedback");
        String worstFeedback = answers.get(worstScoreIndex).get("Feedback");
        int totalAverage = totalSum / count;
        send("PRINCIPAL", new SummaryReadyMessage(totalAverage, bestFeedback, worstFeedback));
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
        String range = String.format("Odgovori!A%d:E%d", sviOdgovori.size()+2, sviOdgovori.size()+2);
        List<String> data = List.of(mail, gender, String.valueOf(age), String.valueOf(rating), feedback);
        List<List<String>> values = List.of(data);
        sviOdgovori.add(data);
        GSheetsAdapterAPI.updateSheet(values, range);
    }
}