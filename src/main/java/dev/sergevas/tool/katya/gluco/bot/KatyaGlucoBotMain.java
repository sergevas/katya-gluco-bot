package dev.sergevas.tool.katya.gluco.bot;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class KatyaGlucoBotMain implements QuarkusApplication {
    @Override
    public int run(String... args) {
        Quarkus.waitForExit();
        return 0;
    }
}
