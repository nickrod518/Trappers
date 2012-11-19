package edu.unca.nrodrigu.Trappers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrappersCommandExecutor implements CommandExecutor {
	@SuppressWarnings("unused")
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

		// stub command
		} else if (command.getName().equalsIgnoreCase("trap")) {
			return true;

		} else {
			return false;
		}
	}
}