package dev.sergevas.tool.katya.gluco.bot.nightscout.control;

import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

public interface NsEntryPublisher {

    void publish(NsEntry nsEntry);
}
