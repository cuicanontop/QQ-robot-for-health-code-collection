package love.simbot.example.Manager;

import love.simbot.example.SimbotExampleApplication;
import love.simbot.example.util.infolog;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private  static String Teacheraccount;


    public static String getTeacheraccount() {
        return Teacheraccount;
    }
    public static void loadConfig() {
        try {
            File file = new File(".//Config.properties");
            if (!file.exists()) {
                file.createNewFile();
                writeConfig("Teacheraccount", "1571330014-1298012208-376846934-915578913-317279508-2603297520-2536959663");
            }

            Properties properties = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(".//Config.properties"));
            properties.load(bufferedReader);

            properties.load(new FileInputStream("./Config.properties"));

            Teacheraccount = properties.getProperty("Teacheraccount");
            infolog.Configinfo("Config is Loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void DetectionConfiguration(){
        if(Teacheraccount==null||Teacheraccount.equals("")){
            infolog.ConfigError("[MasterAccount]The master account cannot be null");
            System.exit(0);
        }
    }

    public static void writeConfig(String key, String value) {
        try {
            File file = new File(".//Config.properties");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(key + "=" + value);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
