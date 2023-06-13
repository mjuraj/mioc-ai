package com.mindsmiths.dashboard.models;

import com.mindsmiths.sdk.core.db.DataModel;
import com.mindsmiths.sdk.core.db.Database;
import com.mindsmiths.sdk.core.db.PrimaryKey;
import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DataModel(emit = true)
public class AssistantConfiguration implements Serializable {
    @PrimaryKey
    String configId;
    String name;
    String introText;
    String qaPrompt;
    List<String> knowledgeBaseIds;
    boolean defaultAssistant;
    String defaultManagerId;
    String model;
    String languageCode;
    Integer maxTokens;
    Double temperature;
    Double topP;

    public static AssistantConfiguration get(String configId) {
        return Database.get(Filters.eq("configId", configId), AssistantConfiguration.class);
    }

    public static ArrayList<AssistantConfiguration> getAll() {
        ArrayList<AssistantConfiguration> result = new ArrayList<>();
        Database.all(AssistantConfiguration.class).iterator().forEachRemaining(result::add);
        return result;
    }
}