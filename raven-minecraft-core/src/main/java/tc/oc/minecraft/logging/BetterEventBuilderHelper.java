package tc.oc.minecraft.logging;

import java.util.logging.LogRecord;

import net.kencochrane.raven.event.Event;
import net.kencochrane.raven.event.helper.EventBuilderHelper;

public interface BetterEventBuilderHelper {
    void helpBuildingEvent(Event event, LogRecord logRecord);
}
