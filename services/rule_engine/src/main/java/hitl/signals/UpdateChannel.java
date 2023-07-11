package hitl.signals;

import com.mindsmiths.sdk.core.api.Message;
import hitl.models.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChannel extends Message {
    String channelId;
    String channelName;
    ChannelType channelType;
    UpdateType updateType;
    String categoryId;

    public UpdateChannel(String channelId, UpdateType updateType) {
        this.channelId = channelId;
        this.updateType = updateType;
    }

    public UpdateChannel(UpdateType updateType) {
        this.updateType = updateType;
    }

    public UpdateChannel(String channelName, UpdateType updateType, String categoryId, ChannelType channelType) {
        this.channelName = channelName;
        this.updateType = updateType;
        this.categoryId = categoryId;
        this.channelType = channelType;
    }
}

