package love.simbot.example;


import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.annotation.SimbotResource;
import love.forte.simbot.core.SimbotApp;
import love.simbot.example.Manager.ConfigManager;
import love.simbot.example.util.infolog;


import java.io.*;


@SimbotApplication({
        @SimbotResource(value = "simbot.yml", orIgnore = true),
        @SimbotResource(value = "simbot-dev.yml", orIgnore = true, command = "dev"),
})
// @SimbotApplication
public class SimbotExampleApplication {

    public static void main(String [] args) throws IOException {

        ConfigManager.loadConfig();
        ConfigManager.DetectionConfiguration();
        infolog.init();
        SimbotApp.run(SimbotExampleApplication.class, args);
        System.out.println("(c) 2021 Cuican Studio, ALL RIGHTS RESERVED.");




    }




}
