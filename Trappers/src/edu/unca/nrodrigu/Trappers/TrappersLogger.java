package edu.unca.nrodrigu.Trappers;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

public class TrappersLogger {
	private final Trappers plugin;
	private final Logger logger;

	public TrappersLogger(Trappers plugin) {
		this.plugin = plugin;
		logger = Logger.getLogger("Minecraft");
	}

	private String buildMessage(String message) {
		PluginDescriptionFile yml = plugin.getDescription();
		String output = yml.getName() + yml.getVersion() + ": " + message;
		return output;
	}

	public void info(String msg) {
		logger.info(this.buildMessage(msg));
	}

	public void warn(String msg) {
		logger.warning(this.buildMessage(msg));
	}
}