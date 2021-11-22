package love.simbot.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InTimer {
    public static String getDate(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(new Date());
    }
}
