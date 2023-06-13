package com.mindsmiths.dashboard.api;

import com.mindsmiths.dashboard.models.Manager;
import com.mindsmiths.sdk.core.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrCreateManager extends Message {
    Manager manager;
}
