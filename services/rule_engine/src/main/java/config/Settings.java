package config;

import java.util.Map;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class Settings {
    public static final Map<String, String> env = System.getenv();
    public static String ARMORY_SITE_URL = env.get("ARMORY_SITE_URL");
    public static String PRINCIPAL_EMAIL = env.get("PRINCIPAL_EMAIL");
    public static String DEFAULT_TIME_ZONE;
    public String ONBOARDING_EMAIL_TEMPLATE = "";
    public String PRINCIPAL_SUMMARY_EMAIL_TEMPLATE = "";
    public String PRINCIPAL_SUMMARY_NO_ENTRIES_EMAIL_TEMPLATE = "";

    static {
        DEFAULT_TIME_ZONE = env.getOrDefault("DEFAULT_TIME_ZONE", "Europe/Zagreb");
    }

    private static Settings single_instance = null;
    public static synchronized Settings getInstance()
    {
        if (single_instance == null)
            single_instance = new Settings();
  
        return single_instance;
    }
}