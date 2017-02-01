package tc.oc.bukkit.logging;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

class BukkitLogFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        if(record.getSourceMethodName().endsWith("fixTileEntity")) {
            // Block has the wrong tile entity, not relevant to admins
            return false;
        }

        return true;
    }
}
