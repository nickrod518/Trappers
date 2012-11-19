package edu.unca.nrodrigu.Trappers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TrappersEventListener implements Listener {
	private final Trappers plugin;

	/*
	 * This listener needs to know about the plugin which it came from
	 */
	public TrappersEventListener(Trappers plugin) {
		// Register the listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		this.plugin = plugin;
	}

	/*
	 * Send a welcome message and inform others of new players
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.logger.info("Give a warm welcome to " + event.getPlayer().getName());
		event.getPlayer().sendMessage("Welcome " + event.getPlayer().getName() + ", enjoy your stay!");
		Player player = event.getPlayer();
		plugin.setMetadata(player, "trapper", true, plugin);
	}
	
	/*
	 * If player interacts with a tree, set a trap on it
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void trap(PlayerInteractEvent event) {
		if ((Boolean) plugin.getMetadata(event.getPlayer(), "trapper", plugin)) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block b = event.getClickedBlock();
				if (b != null) {
					
					// if it's wood, make a tree trap
					if (b.getTypeId() == 17){
						Location loc = b.getLocation();
						
						// find the block under the tree
						while (loc.getBlock().getTypeId() == 17) {
							loc.setY(loc.getY() - 1);
						}
						
						// change block to a lever
						loc.getBlock().setType(Material.LEVER);
						// set lever on ceiling
						loc.getBlock().setData((byte) 0x0);
						
						loc.setY(loc.getY() - 1);
						loc.getBlock().setType(Material.WOOD_PLATE);
						
						// start in corner, and wrap TNT around wood plate
						loc.setX(loc.getX() - 1);
						loc.setZ(loc.getZ() - 1);
						loc.getBlock().setType(Material.TNT);
						for (int i = 0; i < 2; ++i) {
							loc.setZ(loc.getZ() + 1);
							loc.getBlock().setType(Material.TNT);
						}
						for (int i = 0; i < 2; ++i) {
							loc.setX(loc.getX() + 1);
							loc.getBlock().setType(Material.TNT);
						}
						for (int i = 0; i < 2; ++i) {
							loc.setZ(loc.getZ() - 1);
							loc.getBlock().setType(Material.TNT);
						}
						for (int i = 0; i < 2; ++i) {
							loc.setX(loc.getX() - 1);
							loc.getBlock().setType(Material.TNT);
						}
						
						event.getPlayer().sendMessage("Now, we wait...");
						plugin.logger.info("Everyone should go cut some trees down!");
						
					// if it's any other block, plant a mine (TNT under pressure plate)
					} else {
						Location loc = b.getLocation();
						loc.getBlock().setType(Material.TNT);
						loc.setY(loc.getY() + 1);
						loc.getBlock().setType(Material.WOOD_PLATE);
						
						event.getPlayer().sendMessage("Now, we wait...");
						plugin.logger.info("Watch your step!");
					}
				}
			}
		}
	}
}