package signals;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Reminder implements Serializable {
    private String reminder;
    private LocalDateTime dateTime;
}
