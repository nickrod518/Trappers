package edu.unca.nrodrigu.Trappers;

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

		// grant permission for player to use traps
		} else if (command.getName().equalsIgnoreCase("trapper") && sender.hasPermission("trappers.trapper")) {
			Player player;
			// if no player arg given, activate on sender
			if (args.length == 0) {
				player = (Player) sender;
			} else {
				player = plugin.getServer().getPlayer(args[0]);
				if (player == null || !player.isOnline()) {
					sender.sendMessage("Player not found!");
					return false;
				}
			}
			
			// clear player's inventory
			player.getInventory().clear();
			
			if (!(Boolean) plugin.getMetadata(player, "trapper", plugin)) {
				plugin.setMetadata(player, "trapper", true, plugin);
				sender.sendMessage("You are now a trapper! Use shears to lay mines or tree traps.");
				plugin.logger.info(player.getName() + " is a trapper.");
				
				// restore the player's health and food
				player.setHealth(20);
				player.setFoodLevel(20);
				
				// equip the trapper
				player.setItemInHand(new ItemStack(Material.SHEARS, 1));
				player.getInventory().addItem(new ItemStack(Material.BOW, 1));
				player.getInventory().addItem(new ItemStack(Material.ARROW, 40));
				player.getInventory().addItem(new ItemStack(Material.MAP, 1));
				player.getInventory().addItem(new ItemStack(Material.WATCH, 1));
				player.getInventory().addItem(new ItemStack(Material.COMPASS, 1));
				player.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
				player.getInventory().addItem(new ItemStack(Material.SHEARS, 1));
				
				// armor
				player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
				
				// food
				player.getInventory().addItem(new ItemStack(Material.BREAD, 2));
				player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 1));
				player.getInventory().addItem(new ItemStack(Material.APPLE, 1));
				
			} else {
				plugin.setMetadata(player, "trapper", false, plugin);
				sender.sendMessage("You are no longer a trapper.");
				plugin.logger.info(player.getName() + " is no longer a trapper.");
			}

			return true;

		} else {
			return false;
		}
	}
}