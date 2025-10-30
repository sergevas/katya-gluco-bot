package dev.sergevas.tool.katya.gluco.bot.support;

import dev.sergevas.tool.katya.gluco.bot.KatyaGlucoBotMain;
import org.springframework.boot.SpringApplication;

public class TestKatyaGlucoBotMain {
    public static void main(String[] args) {
        SpringApplication.from(KatyaGlucoBotMain::main)
                .with(TestContainersConfig.class)
                .run(args);
    }
}
