package com.mindsmiths.dashboard.api;

import com.mindsmiths.dashboard.models.Client;
import com.mindsmiths.sdk.core.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrCreateClient extends Message {
    Client client;
}
