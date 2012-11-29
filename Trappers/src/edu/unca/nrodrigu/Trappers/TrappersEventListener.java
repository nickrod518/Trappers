package edu.unca.nrodrigu.Trappers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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
		Player player = event.getPlayer();
		plugin.logger.info("Give a warm welcome to " + player.getName());
		player.sendMessage("Be careful, " + player.getName() + ", you are being hunted!");
		plugin.setMetadata(player, "trapper", false, plugin);
		
		// lower player's health and food and clear inventory
		player.setHealth(10);
		player.setFoodLevel(10);
		player.getInventory().clear();
	}
	
	/*
	 * Allow trappers to set traps
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void trap(PlayerInteractEvent event) {
		// get player that event is called on
		Player player = event.getPlayer();
		
		// check that the player is a trapper
		if ((Boolean) plugin.getMetadata(event.getPlayer(), "trapper", plugin)) {
			
			// check that the trapper has shears in their hand
			if (player.getItemInHand().getType() == Material.SHEARS) {
				
				// if they left clicked, create a tree stand
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					Block b = event.getClickedBlock();
					
					// if you left clicked a tree, place a ladder on it
					if (b != null && b.getTypeId() == 17) {
						BlockFace clickedFace = event.getBlockFace();
						byte data = 0;
						Location loc = b.getLocation();
						
						// set loc to the bottom of the tree
						while (loc.getBlock().getTypeId() == 17) {
							loc.setY(loc.getY() - 1);
						}
						
						// loc2 will be used to place the ladder
						Location loc2 = loc;
						
						// use the clicked face to find what side to put the ladder on
						if (clickedFace == BlockFace.NORTH) {
							loc2.setZ(loc2.getZ() + 1);
							data = 0x2;
						} else if (clickedFace == BlockFace.SOUTH) {
							loc2.setZ(loc2.getZ() - 1);
							data = 0x3;
						} else if (clickedFace == BlockFace.WEST) {
							loc2.setX(loc2.getX() - 1);
							data = 0x4;
						} else if (clickedFace == BlockFace.EAST) {
							loc2.setX(loc2.getX() + 1);
							data = 0x5;
						}
						
						// put a ladder up the side of the tree
						while (loc.getBlock().getTypeId() == 17) {
							loc2.getBlock().setTypeIdAndData(65, data, true);
							loc.setY(loc.getY() + 1);
							loc2.setY(loc.getY());
						}
					}
				}
				
				// if they right clicked, lay a trap
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
							
							// change block to a lever and set on ceiling
							loc.getBlock().setTypeIdAndData(Material.LEVER.getId(), (byte) 0x0, true);
							
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
							
							player.sendMessage("Now, we wait...");
							plugin.logger.info("Everyone should go cut some trees down!");
							
						// if it's any other block, plant a mine (TNT under pressure plate)
						} else {
							Location loc = b.getLocation();
							loc.getBlock().setType(Material.TNT);
							loc.setY(loc.getY() + 1);
							loc.getBlock().setType(Material.WOOD_PLATE);
							
							player.sendMessage("Now, we wait...");
							plugin.logger.info("Watch your step!");
						}
					}
				}
			}
		}
	}
	
	/*
	 * Sends messages and logs if an entity is hurt by a trap
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onTrap(EntityDamageEvent event) {
		
		// if the player was hurt by an explosion, it was a trap
		if (event.getCause() == DamageCause.BLOCK_EXPLOSION) {

			// get the damagee
			Entity damagee = event.getEntity();
			// if damagee was a player
			if (damagee instanceof Player) {
				Player player = (Player) damagee;
				plugin.logger.info(player.getName() + " was hurt by a trap!");
				player.sendMessage("Look out for traps!");
				
			// damagee was a mob
			} else if (damagee instanceof LivingEntity){
				plugin.logger.info("That poor " + damagee.getType() + " did nothin' to nobody!");
			}
		}
	}
}