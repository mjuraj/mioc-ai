package com.mindsmiths.dashboard.models;

import java.util.List;

import com.mindsmiths.sdk.core.db.DataModel;
import com.mindsmiths.sdk.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DataModel(emit = true)
public class Client implements PersonInterface {
    String id;
    String firstName;
    String lastName;
    String description;
    String phoneNumber;
    String email;
    String managerId;
    String timeZone;
    String assistantConfigurationId;

    public Client(String firstName, String phoneNumber) {
        this.id = Utils.randomString();
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public Client(String id, String firstName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public Client(String id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
