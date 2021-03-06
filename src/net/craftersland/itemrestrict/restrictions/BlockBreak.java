package net.craftersland.itemrestrict.restrictions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import net.craftersland.itemrestrict.ItemRestrict;
import net.craftersland.itemrestrict.RestrictedItemsHandler.ActionType;
import net.craftersland.itemrestrict.utils.RestrictedItem;

public class BlockBreak implements Listener {
	
	private ItemRestrict ir = ItemRestrict.get();
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onBlockBreak(BlockBreakEvent event) {
		if (ir.blockBreakBanned.size() != 0) {
			boolean compareDrops = false;
			ItemStack itemToDrop = null;
			if (event.getBlock().getDrops().iterator().hasNext()) {
				itemToDrop = event.getBlock().getDrops().iterator().next();
				if (event.getBlock().getType() == itemToDrop.getType()) {
					compareDrops = true;
				}
			}
			RestrictedItem bannedInfo = null;
			if (!compareDrops) {
				bannedInfo = ir.getRestrictedItemsHandler().isBanned(ActionType.BLOCKBREAK, event.getPlayer(), event.getBlock().getType(), /*event.getBlock().getState().getData().getData(), */event.getPlayer().getLocation());
			} else if (itemToDrop != null) {
				bannedInfo = ir.getRestrictedItemsHandler().isBanned(ActionType.BLOCKBREAK, event.getPlayer(), itemToDrop.getType(), /*itemToDrop.getDurability(), */event.getPlayer().getLocation());
			}
			
			if (bannedInfo != null) {
				event.setCancelled(true);
				//p.getWorld().dropItem(p.getLocation(), item);
				//p.setItemInHand(null);
				
				ir.getSoundHandler().sendPlingSound(event.getPlayer());
				ir.getConfigHandler().printMessage(event.getPlayer(), "chatMessages.blockBreakRestricted", bannedInfo.reason);
			}
		}
	}

}
