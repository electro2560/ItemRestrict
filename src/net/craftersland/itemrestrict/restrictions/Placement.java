package net.craftersland.itemrestrict.restrictions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.craftersland.itemrestrict.ItemRestrict;
import net.craftersland.itemrestrict.RestrictedItemsHandler.ActionType;
import net.craftersland.itemrestrict.utils.RestrictedItem;

public class Placement implements Listener {
	
	private ItemRestrict ir = ItemRestrict.get();
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onInteract(BlockPlaceEvent event) {
		if (ir.placementBanned.size() != 0) {	
			RestrictedItem bannedInfo = ir.getRestrictedItemsHandler().isBanned(ActionType.PLACEMENT, event.getPlayer(), event.getItemInHand().getType(), /*event.getItemInHand().getDurability(), */event.getPlayer().getLocation());
			
			if (bannedInfo != null) {
				event.setCancelled(true);
				//p.getWorld().dropItem(p.getLocation(), item);
				//p.setItemInHand(null);
				
				ir.getSoundHandler().sendEndermanTeleportSound(event.getPlayer());
				ir.getConfigHandler().printMessage(event.getPlayer(), "chatMessages.placementRestricted", bannedInfo.reason);
			} else if (ir.is19Server) {
				RestrictedItem bannedInfo2 = ir.getRestrictedItemsHandler().isBanned(ActionType.PLACEMENT, event.getPlayer(), event.getPlayer().getInventory().getItemInOffHand().getType(), /*event.getPlayer().getInventory().getItemInOffHand().getDurability(), */event.getPlayer().getLocation());
				if (bannedInfo2 != null) {
					event.setCancelled(true);
					ir.getSoundHandler().sendEndermanTeleportSound(event.getPlayer());
					ir.getConfigHandler().printMessage(event.getPlayer(), "chatMessages.placementRestricted", bannedInfo2.reason);
				}
			}
		}
	}

}
