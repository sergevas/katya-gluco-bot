package dev.sergevas.tool.katya.gluco.bot.application.service.shared.parser;

import java.nio.file.Files;
import java.nio.file.Path;

public class ParserTestSupport {

    public static byte[] readRawTestSensorData() {
        try {
            return Files.readAllBytes(Path.of("src/test/resources/polls01.dat"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
