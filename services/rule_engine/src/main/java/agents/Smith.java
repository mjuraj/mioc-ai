
package agents;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import com.mindsmiths.gsheetsAdapter.GSheetsAdapterAPI;
import com.mindsmiths.gsheetsAdapter.reply.Spreadsheet;
import com.mindsmiths.ruleEngine.model.Agent;
import com.mindsmiths.ruleEngine.util.Log;

import config.Settings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import signals.SummaryReadyMessage;


@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class Smith extends Agent {

    LocalDateTime lastTableUpdate = null;
    int sheetSize = 0;
    int nextAnsRow = 0;
    int nextEmailRow = 0;
    int nextNumberRow = 0;

    boolean summaryRequested = false;
    LocalDateTime summaryFromTimestamp;


    // Agent id
    public Smith() {
        this.id = "SMITH";
    }

    public void processSpreadsheet(Spreadsheet spreadsheet) {
        Log.info(spreadsheet);
        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");

        sheetSize = answers.size();
        nextAnsRow = answers.size() + 2;
    }

    public void generateSummaryFromData(Spreadsheet spreadsheet) {
        List<Map<String, String>> answers = spreadsheet.getSheets().get("Odgovori");

        int totalSum = 0;
        int count = 0;

        String feedback = "";
        for (Map<String, String> item : answers) {
            long timestamp = Long.parseLong(item.get("Timestamp"));
            if (summaryFromTimestamp != null && timestamp <= summaryFromTimestamp.atZone(ZoneId.of(Settings.DEFAULT_TIME_ZONE)).toInstant().toEpochMilli()) {
                continue;
            }
            int rating = Integer.parseInt(item.get("Rating"));

            if (feedback.length() < 500) {
                feedback += item.get("Feedback") + "\n";
            }

            totalSum += rating;
            count++;
        }

        if (count == 0) {
            send("PRINCIPAL", new SummaryReadyMessage(count, -1, "NO_NEW_FEEDBACK"));
        } else {
            int totalAverage = totalSum / count;
            send("PRINCIPAL", new SummaryReadyMessage(count, totalAverage, feedback));
        }
    }

    public void addReviewToSheet(String gender, Integer age, Integer rating, String feedback, Long timestamp) {
        String range = String.format("Odgovori!A%d:E%d", nextAnsRow, nextAnsRow);
        List<String> data = List.of(gender, String.valueOf(age), String.valueOf(rating), feedback, String.valueOf(timestamp));
        List<List<String>> values = List.of(data);
        GSheetsAdapterAPI.updateSheet(values, range);
        nextAnsRow++;
    }
}
