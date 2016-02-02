package tc.oc.bungee.logging;

import java.net.InetSocketAddress;
import java.util.logging.LogRecord;
import javax.annotation.Nullable;

import net.kencochrane.raven.event.EventBuilder;
import tc.oc.minecraft.logging.BetterRaven;

public class BungeeRavenHelper implements BetterRaven.Helper {
    @Override
    public void helpBuildingEvent(EventBuilder eventBuilder, @Nullable LogRecord logRecord) {
        if(logRecord.getParameters() != null) {
            for(Object param : logRecord.getParameters()) {
                if(param instanceof InetSocketAddress) {
                    eventBuilder.addTag("remote_ip", ((InetSocketAddress) param).getAddress().toString());
                }
            }
        }
    }
}
