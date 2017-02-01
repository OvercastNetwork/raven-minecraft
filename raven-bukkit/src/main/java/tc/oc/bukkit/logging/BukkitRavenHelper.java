package tc.oc.bukkit.logging;

import net.kencochrane.raven.event.EventBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EntityAction;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.vehicle.VehicleEvent;
import tc.oc.minecraft.logging.BetterRaven;

import java.util.logging.LogRecord;
import javax.annotation.Nullable;

/**
 * Attaches all sorts of juicy Bukkit info to Sentry events
 */
class BukkitRavenHelper implements BetterRaven.Helper {
    @Override
    public void helpBuildingEvent(EventBuilder eventBuilder, @Nullable LogRecord record) {
        if(record != null) {
            // For EventExceptions, extract some extra details from the Event
            if(record.getThrown() instanceof EventException) {
                Event event = ((EventException) record.getThrown()).getEvent();
                if(event != null) {
                    // Dump the Event itself to the message
                    eventBuilder.addExtra("Bukkit Event", event);

                    // If the event was caused by a player, add tags for their name and locale
                    Player player = getPlayer(event);
                    if(player != null) {
                        eventBuilder.addExtra("Bukkit Player", player);
                        eventBuilder.addTag("player_username", player.getName());
                        eventBuilder.addTag("player_locale", player.getLocale());
                    }

                    // Extract various helpful properties from the event
                    Entity entity = null;
                    if(event instanceof EntityEvent) {
                        entity = ((EntityEvent) event).getEntity();
                        if(entity != player) {
                            eventBuilder.addExtra("Bukkit Entity", entity);
                        }
                    }

                    if(event instanceof EntityAction) {
                        Entity actor = ((EntityAction) event).getActor();
                        if(actor != entity && actor != player) {
                            eventBuilder.addExtra("Bukkit Entity (actor)", actor);
                        }
                    }

                    if(event instanceof BlockEvent) {
                        eventBuilder.addExtra("Bukkit Block", ((BlockEvent) event).getBlock());
                    }

                    if(event instanceof InventoryEvent) {
                        eventBuilder.addExtra("Bukkit Inventory", ((InventoryEvent) event).getInventory());
                        eventBuilder.addExtra("Bukkit InventoryHolder", ((InventoryEvent) event).getInventory().getHolder());
                    }

                    if(event instanceof VehicleEvent) {
                        eventBuilder.addExtra("Bukkit Vehicle", ((VehicleEvent) event).getVehicle());
                    }

                    if(event instanceof HangingEvent) {
                        eventBuilder.addExtra("Bukkit Hanging", ((HangingEvent) event).getEntity());
                    }
                }
            }
        }
    }

    /**
     * Try really hard to extract a {@link Player} from the given Event
     */
    private static Player getPlayer(Event event) {
        if(event instanceof PlayerEvent) {
            return ((PlayerEvent) event).getPlayer();
        } else if(event instanceof EntityEvent && ((EntityEvent) event).getEntity() instanceof Player) {
            return (Player) ((EntityEvent) event).getEntity();
        } else if(event instanceof InventoryInteractEvent && ((InventoryInteractEvent) event).getWhoClicked() instanceof Player) {
            return (Player) ((InventoryInteractEvent) event).getWhoClicked();
        } else {
            try {
                Object thing = event.getClass().getMethod("getPlayer").invoke(event);
                return thing instanceof Player ? (Player) thing : null;
            }
            catch(ReflectiveOperationException e) {
                return null;
            }
        }
    }
}
