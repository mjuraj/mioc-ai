package com.mindsmiths.dashboard.models;

import com.mindsmiths.sdk.core.db.DataModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DataModel(emit = true)
public class Manager implements PersonInterface {
    String id;
    String firstName;
    String lastName;
    String description;
    String phoneNumber;
    String email;
    String emailProvider;
    String authUrl;
    String timeZone;
    String hitlHandle;
    boolean authenticated;
    String assistantConfigurationId;
}
