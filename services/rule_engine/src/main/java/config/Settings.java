package config;

import java.util.Map;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class Settings {
    public static final Map<String, String> env = System.getenv();
    public static String ARMORY_SITE_URL = env.get("ARMORY_SITE_URL");
}