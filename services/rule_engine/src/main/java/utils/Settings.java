package utils;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class Settings {
    public static final Map<String, String> env = System.getenv();
    public static String ARMORY_SITE_URL = env.get("ARMORY_SITE_URL");
    public static String INFOBIP_PHONE_NUMBER = env.get("INFOBIP_PHONE_NUMBER");
    public static String MODULE = env.get("MODULE");
    public static String DEFAULT_TIME_ZONE = env.getOrDefault("DEFAULT_TIME_ZONE", "America/New_York");
}