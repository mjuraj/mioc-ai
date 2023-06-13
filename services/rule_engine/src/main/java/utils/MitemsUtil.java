package utils;

import com.mindsmiths.infobipAdapter.api.Button;
import com.mindsmiths.mitems.Option;
import com.mindsmiths.sdk.utils.templating.Templating;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MitemsUtil {

    public static List<Button> optionsToButtons(Option[] options, Map<String, Object> context) {
        return Arrays.stream(options)
                .map(option -> new Button(option.getId(), Templating.recursiveRender(option.getText(), context))).collect(Collectors.toList());
    }

}
