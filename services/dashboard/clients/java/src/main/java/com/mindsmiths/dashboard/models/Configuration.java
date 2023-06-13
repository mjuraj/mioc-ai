package com.mindsmiths.dashboard.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.mindsmiths.sdk.core.db.DataModel;
import com.mindsmiths.sdk.core.db.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DataModel(emit = true)
public class Configuration implements Serializable {
    String id;
    JsonNode screens;
}
