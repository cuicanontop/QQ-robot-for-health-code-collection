package love.simbot.example.util;

import love.forte.common.ioc.annotation.Beans;
import love.simbot.example.Manager.ConfigManager;
import love.simbot.example.listener.MyGroupListen;
import love.simbot.example.listener.MyIncidentresponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Beans
public class infolog {

    private static final Logger LOGinfo = LoggerFactory.getLogger(MyGroupListen.class);
    private static final Logger LOGMyIncidentresponse = LoggerFactory.getLogger(MyIncidentresponse.class);
    private static final Logger Config = LoggerFactory.getLogger(ConfigManager.class);
    public static void Ginfoinfo(String log){
        LOGinfo.info("[INFO]:"+log);
        writeLog("[" + getDate() + "] [INFO]:"+log);
    }
    public static void Configinfo(String log){
        Config.info("[INFO]:"+log);
        writeLog("[INFO]:"+log);
    }
    public static void ConfigError(String log){
        Config.error("[ERROR]:" +log);
        writeLog("[" + getDate() + "] [ERROR]:" +log);
    }

    public static void LOGMyIncidentresponseinfo(String log){
        LOGMyIncidentresponse.info("[INFO]:"+log);
        writeLog("[" + getDate() + "] [INFO]:"+log);
    }
    public static void GinfoError(String log){
        LOGinfo.error("[ERROR]:" +log);
        writeLog("[" + getDate() + "] [ERROR]:" +log);
    }


    public static void init() {
        try {
            File folder = new File("./log");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String result = getDate2("yyyy-MM-dd");
            File file = new File("./log/" + result + ".log");
            if (!file.exists()) {
                file.createNewFile();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDate2(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(new Date());
    }
    public static String getLogFileName() throws IOException {
        String result = getDate2("yyyy-MM-dd");
        return "./log/" + result + ".log";
    }
    public static void writeLog(String str) {
        try {
            File file = new File(getLogFileName());
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(str);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date());
    }
}
