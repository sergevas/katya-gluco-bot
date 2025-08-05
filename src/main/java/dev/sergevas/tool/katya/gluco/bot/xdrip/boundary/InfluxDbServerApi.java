package dev.sergevas.tool.katya.gluco.bot.xdrip.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xdrip")
public class InfluxDbServerApi {

    private static final Logger LOG = LoggerFactory.getLogger(InfluxDbServerApi.class);

    @PostMapping(path = "/readings",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String postReading(@RequestBody String reading) {
        LOG.info("Received request to /reading {}", reading);
        return """
                {"status":"ok"}""";
    }
}
