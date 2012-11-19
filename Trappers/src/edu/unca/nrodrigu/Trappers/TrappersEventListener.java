package edu.unca.nrodrigu.Trappers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
		Player fred = event.getPlayer();
		plugin.setMetadata(fred, "test", false, plugin);
	}
}