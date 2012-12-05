package edu.unca.nrodrigu.Trappers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrappersCommandExecutor implements CommandExecutor {
	private final Trappers plugin;
	
	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public TrappersCommandExecutor(Trappers plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;

		// grant permission for player to use trapper commands
		} else if (command.getName().equalsIgnoreCase("trapper") && sender.hasPermission("trappers.trapper")) {
			Player player;
			// if no player arg given, activate on sender
			if (args.length == 0) {
				player = (Player) sender;
			} else {
				player = plugin.getServer().getPlayer(args[0]);
				if (player == null || !player.isOnline()) {
					sender.sendMessage(ChatColor.RED + "Player \'" + args[0] + "\' not found!");
					return false;
				}
			}

			// clear player's inventory
			player.getInventory().clear();

			if (!(Boolean) plugin.getMetadata(player, "trapper", plugin)) {
				plugin.setMetadata(player, "trapper", true, plugin);
				player.sendMessage(ChatColor.GREEN + "You are now a trapper! Shears are your new best friend!");
				plugin.logger.info(player.getName() + " is a trapper.");

				// restore the player's health and food
				player.setHealth(20);
				player.setFoodLevel(20);

				// tools
				player.setItemInHand(new ItemStack(Material.SHEARS, 1));
				player.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
				player.getInventory().addItem(new ItemStack(Material.BOW, 1));
				player.getInventory().addItem(new ItemStack(Material.ARROW, 40));
				player.getInventory().addItem(new ItemStack(Material.MAP, 1));
				player.getInventory().addItem(new ItemStack(Material.WATCH, 1));
				player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));

				// armor
				player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));

				// food
				player.getInventory().addItem(new ItemStack(Material.BREAD, 2));
				player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 1));
				player.getInventory().addItem(new ItemStack(Material.APPLE, 1));
				player.getInventory().addItem(new ItemStack(Material.BOWL, 1));

				// trap money
				player.getInventory().addItem(new ItemStack(388, 25));

			} else {
				plugin.setMetadata(player, "trapper", false, plugin);
				sender.sendMessage(ChatColor.RED + "You are no longer a trapper.");
				plugin.logger.info(player.getName() + " is no longer a trapper.");
			}

			return true;

		// generate leaves around player
		} else if (command.getName().equalsIgnoreCase("camo") && sender.hasPermission("trappers.camo")) {
			if ((Boolean) plugin.getMetadata((Player) sender, "trapper", plugin)) {
				if (((Player) sender).getInventory().contains(Material.EMERALD, 3)) {
					Player player = (Player) sender;
					Location loc = player.getLocation();
	
					// increment through too many for loops to surround player with leaves
					for (int i = 0; i < 3; ++i) {
						for (int j = 0; j < 3; ++j) {
							for (int k = 0; k < 3; ++k) {
	
								// about a 75% change that leaves will generate at this location
								if (((int) (Math.random() * 100)) < 75) {
									loc.getBlock().setType(Material.LEAVES);
								}
								loc.setX(loc.getX() + 1);
							}
							loc.setX(player.getLocation().getX() - 1);
							loc.setZ(loc.getZ() + 1);
						}
						loc.setZ(player.getLocation().getZ() - 1);
						loc.setY(loc.getY() + 1);
					}
					
					// costs 1 emerald
					((Player) sender).getInventory().removeItem(new ItemStack[] {
							new ItemStack(Material.getMaterial(388), 3) });
					((Player) sender).sendMessage("That cost 3 emerald.");
					
				} else {
					sender.sendMessage(ChatColor.RED + "You don't have enough emeralds!");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Only trappers can use that command.");
			}

			return true;

		// return the specified player's coordinates
		} else if (command.getName().equalsIgnoreCase("track") && sender.hasPermission("trappers.track")) {
			/* if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "You forgot to give a name!");
				
				return false;
			} */
			
			if (args.length == 0) {

				Player player = (Player) sender;
				
				sender.sendMessage(ChatColor.GREEN + "X: " + (int) player.getLocation().getX()
														+ " Y: " + (int) player.getLocation().getY()
														+ " Z: " + (int) player.getLocation().getZ());
				
				return true;
				
			} else {
			
			
				if ((Boolean) plugin.getMetadata((Player) sender, "trapper", plugin)) {
					if (((Player) sender).getInventory().contains(Material.EMERALD, 5)) {
						Player player = plugin.getServer().getPlayer(args[0]);
	
						if (player == null || !player.isOnline()) {
							sender.sendMessage(ChatColor.RED + "Player \'" + args[0] + "\' not found!");
							return false;
	
						} else {
							// costs 1 emerald
							((Player) sender).getInventory().removeItem(new ItemStack[] {
									new ItemStack(Material.getMaterial(388), 5) });
							((Player) sender).sendMessage("That cost 5 emerald.");
	
							// return the player's coordinates
							sender.sendMessage(ChatColor.GREEN + "X: " + (int) player.getLocation().getX()
									+ " Y: " + (int) player.getLocation().getY()
									+ " Z: " + (int) player.getLocation().getZ());
	
							player.sendMessage(ChatColor.RED + "Someone is tracking you...");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You don't have enough emeralds!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Only trappers can use that command.");
				}
	
				return true;	
		
			}
			
		} else if (command.getName().equalsIgnoreCase("score") && sender.hasPermission("trappers.score")) {
			sender.sendMessage(ChatColor.GREEN + "A total of " + plugin.getScore() + " injuries from traps.");
			
			return true;
		}
		
		/* else if (command.getName().equalsIgnoreCase("score")) {
			Player player = (Player) sender;
			String name = player.getName();
			String valueToreg = "5";
			Score scoreClass = plugin.getDatabase().find(Score.class).where().ieq("name",name).ieq("playerName",player.getName()).findUnique();
			if (scoreClass == null) {
				scoreClass = new Score();
				scoreClass.setPlayer(player);
				scoreClass.setName(name);
			}
			scoreClass.setTest(valueToreg);
			plugin.getDatabase().save(scoreClass);
			
			return true;
			
		} else if (command.getName().equalsIgnoreCase("view")) {
			Player player = (Player) sender;
			String name = player.getName();
			Score scoreClass = plugin.getDatabase().find(Score.class).where().ieq("name",name).ieq("playerName",player.getName()).findUnique();
			if (scoreClass == null) {
				player.sendMessage(ChatColor.RED + "That entry doesn't exist.");
				return true;
			}
			player.sendMessage(ChatColor.GREEN + "name: " + scoreClass.getName());
			player.sendMessage(ChatColor.GREEN + "playerName: " + scoreClass.getPlayerName());
			player.sendMessage(ChatColor.GREEN + "Test: " + scoreClass.getTest());
			
			return true;
		}
		*/
		
		else {
			return false;
		}
	}
}