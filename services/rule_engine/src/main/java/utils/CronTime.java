package utils;

import com.mindsmiths.ruleEngine.util.DateUtil;
import config.Settings;

import java.time.LocalDateTime;

public class CronTime {
    public static boolean isSatisfied(String cron, LocalDateTime now) {
        return DateUtil.evaluateCronExpression(cron, now, Settings.DEFAULT_TIME_ZONE);
    }
}