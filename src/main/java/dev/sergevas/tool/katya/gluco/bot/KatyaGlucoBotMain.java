package dev.sergevas.tool.katya.gluco.bot;

import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = {
        TelegramBotProperties.class
})
public class KatyaGlucoBotMain {

    public static void main(String[] args) {
        SpringApplication.run(KatyaGlucoBotMain.class, args);
    }
}
