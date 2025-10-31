package dev.sergevas.tool.katya.gluco.bot;

import dev.sergevas.tool.katya.gluco.bot.recommendation.RecommendationMessagesProperties;
import dev.sergevas.tool.katya.gluco.bot.telegram.TelegramBotProperties;
import dev.sergevas.tool.katya.gluco.bot.xdrip.XdripProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
@ConfigurationPropertiesScan(basePackageClasses = {
        AppProperties.class,
        TelegramBotProperties.class,
        RecommendationMessagesProperties.class,
        XdripProperties.class
})
public class KatyaGlucoBotMain {

    public static void main(String[] args) {
        SpringApplication.run(KatyaGlucoBotMain.class, args);
    }
}
