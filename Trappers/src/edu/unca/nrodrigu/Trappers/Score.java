package edu.unca.nrodrigu.Trappers;
public class Score extends Trappers{}

/*
package edu.unca.nrodrigu.Trappers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;





@Entity()
@Table(name="trappers")





public class Score extends Trappers {
	
	
	@Id
	private int id;
	@NotNull
	private String playerName;
	@Length(max=30)
	@NotEmpty
	private String name;
	@NotEmpty
	private String Test;
	@NotEmpty
	private int score;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(playerName);
	}
	public void setPlayer(Player player) {
		this.playerName = player.getName();
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getTest() {
		return Test;
	}
	public void setTest(String Test) {
		this.Test = Test;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void incScore(int inc) {
		this.score += inc;
	}
}
*/