package tc.oc.minecraft.logging;

import java.util.logging.LogRecord;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import net.kencochrane.raven.event.EventBuilder;

class CoreRavenHelper implements BetterRaven.Helper {

    protected boolean isEligibleCulprit(StackTraceElement frame) {
        // Exclude common guard methods from being the culprit
        return !Preconditions.class.getName().equals(frame.getClassName());
    }

    @Override
    public void helpBuildingEvent(EventBuilder eventBuilder, LogRecord record) {
        if(record != null && record.getThrown() != null) {
            // Always use the root exception
            Throwable thrown = Throwables.getRootCause(record.getThrown());

            // Get the main message from the exception instead of the log record
            eventBuilder.setMessage(thrown.toString());

            // Add some extra details about the log record
            eventBuilder.addExtra("Log message", record.getMessage());
            eventBuilder.addExtra("Logger name", record.getLoggerName());

            // Set the culprit based on the exception, which oddly is not done by default
            for(StackTraceElement frame : thrown.getStackTrace()) {
                if(isEligibleCulprit(frame)) {
                    eventBuilder.setCulprit(frame);
                    break;
                }
            }
        }
    }
}
