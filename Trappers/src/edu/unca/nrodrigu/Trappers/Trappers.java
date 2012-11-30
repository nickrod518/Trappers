package edu.unca.nrodrigu.Trappers;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Trappers extends JavaPlugin {
	TrappersLogger logger;
	TrappersCommandExecutor executor;

	/*
	 * This is called when your plug-in is enabled
	 */
	@Override
	public void onEnable() {
		// create a logger and use it
		logger = new TrappersLogger(this);
		logger.info("plugin enabled");

		// save the configuration file
		saveDefaultConfig();

		// set the command executor for sample
		executor = new TrappersCommandExecutor(this);

		// create the listener
		new TrappersEventListener(this);

		// set the command executors
		this.getCommand("trapper").setExecutor(executor);
		this.getCommand("camo").setExecutor(executor);
		this.getCommand("track").setExecutor(executor);
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {
		logger.info("plugin disabled");
	}

	public void setMetadata(Player player, String key, Object value, Trappers plugin) {
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public Object getMetadata(Player player, String key, Trappers plugin) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(plugin.getDescription().getName())) {
				return (value.asBoolean());
			}
		}
		return null;
	}
}