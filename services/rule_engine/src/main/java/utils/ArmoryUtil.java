package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mindsmiths.armory.ArmoryAPI;
import com.mindsmiths.armory.Screen;
import com.mindsmiths.armory.component.BaseComponent;
import com.mindsmiths.armory.payload.HistoryItem;
import com.mindsmiths.sdk.utils.templating.Templating;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArmoryUtil {
    public static void show(
            String connectionId, String firstScreen, Map<String, Object> context, JsonNode screensSpec,
            List<HistoryItem> history, Map<String, Object> configuration
    ) throws ClassNotFoundException, JsonProcessingException {
        ObjectMapper jsonObjectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ArrayList<Screen> screens = new ArrayList<>();
        ArrayList<String> processedScreens = new ArrayList<>();

        if (context != null && screensSpec != null)
            screensSpec = recursiveRender(screensSpec, context);

        ArrayList<String> unprocessedScreens = new ArrayList<>();
        unprocessedScreens.add(firstScreen);
        for (HistoryItem item : history)
            unprocessedScreens.add(item.getActiveScreenId());
        while (unprocessedScreens.size() > 0) {
            String screenId = unprocessedScreens.remove(0);
            if (processedScreens.contains(screenId))
                continue;
            processedScreens.add(screenId);
            JsonNode screenSpec = screensSpec.get(screenId);
            if (screenSpec == null)
                throw new RuntimeException("Screen " + screenId + " not found in screens specification");
            Screen screen = new Screen(screenId);
            if (screenSpec.has("template"))
                screen.setTemplate(screenSpec.get("template").asText());

            for (Iterator<Map.Entry<String, JsonNode>> it = screenSpec.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> component = it.next();
                if (component.getKey().equals("template"))
                    continue;
                if (!component.getValue().isArray())
                    component.setValue(jsonObjectMapper.valueToTree(List.of(component.getValue())));
                for (JsonNode value : component.getValue()) {
                    if (component.getKey().equals("SubmitButton"))
                        if (value.has("nextScreen"))
                            unprocessedScreens.add(value.get("nextScreen").asText());
                    Class<?> cls = Class.forName("com.mindsmiths.armory.component." + component.getKey());
                    screen.add((BaseComponent) jsonObjectMapper.treeToValue(value, cls));
                }
            }

            screens.add(screen);
        }


        ArmoryAPI.show(connectionId, firstScreen, history, configuration, screens);
    }

    public static JsonNode recursiveRender(JsonNode screenSpec, Map<String, Object> context) {
        if (screenSpec.isObject()) {
            ObjectNode rendered = screenSpec.deepCopy();
            for (Iterator<Map.Entry<String, JsonNode>> it = rendered.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> field = it.next();
                if (field.getValue().isTextual())
                    rendered.putPOJO(field.getKey(), guessType(Templating.recursiveRender(field.getValue().asText(), context)));
                else
                    rendered.set(field.getKey(), recursiveRender(field.getValue(), context));
            }
            return rendered;
        }
        if (screenSpec.isArray()) {
            ArrayNode rendered = screenSpec.deepCopy();
            for (int i = 0; i < rendered.size(); i++)
                if (rendered.get(i).isTextual())
                    rendered.setPOJO(i, guessType(Templating.recursiveRender(rendered.get(i).asText(), context)));
                else
                    rendered.set(i, recursiveRender(rendered.get(i), context));
            return rendered;
        }
        return screenSpec;
    }

    public static Object guessType(String value) {
        if (value == null)
            return null;
        if (value.equals("true") || value.equals("false"))
            return Boolean.parseBoolean(value);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }
        return value;
    }
}
