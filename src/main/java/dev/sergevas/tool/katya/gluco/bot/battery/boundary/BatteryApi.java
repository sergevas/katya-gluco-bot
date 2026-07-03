package dev.sergevas.tool.katya.gluco.bot.battery.boundary;

import dev.sergevas.tool.katya.gluco.bot.battery.entity.Battery;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/battery")
public class BatteryApi {

    private static final Logger LOG = LoggerFactory.getLogger(BatteryApi.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public BatteryApi(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> onBatteryLevelChange(@Valid @NotNull @RequestBody Battery battery) {
        LOG.info("Enter onBatteryLevelChange() battery={}", battery);
        this.applicationEventPublisher.publishEvent(battery);
        LOG.info("Exit onBatteryLevelChange()");
        return ResponseEntity.accepted().build();
    }
}
