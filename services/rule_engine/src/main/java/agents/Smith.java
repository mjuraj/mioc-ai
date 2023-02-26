package agents;

import java.time.LocalDateTime;

import com.mindsmiths.gsheetsAdapter.reply.Spreadsheet;
import com.mindsmiths.ruleEngine.model.Agent;
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

    public Smith() {
        this.id = "SMITH";
    }

    public void processSheet(Spreadsheet spreadsheet) {
        Log.info(spreadsheet);
        sheetSize = spreadsheet.getSheets().get("Odgovori").size();
    }

    public void addReviewToSheet(String mail, String gender, Integer age, Integer rating, String feedback) {
        String range = String.format("Odgovori!A%d:E%d", sheetSize+2, sheetSize+2);
        List<List<String>> values = List.of(List.of(mail, gender, String.valueOf(age), String.valueOf(rating), feedback));
        GSheetsAdapterAPI.updateSheet(values, range);
    }
}
