package me.drkmatr1984.GriefSaveAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.drkmatr1984.GriefSaveAPI.utils.BlockSerialization;

public class DataAccessor{
	
	private GriefSaveAPI plugin;
	private List<String> banList = new ArrayList<String>();
	private Set<Material> banListConverted = new HashSet<Material>();
	public boolean useListeners;
	public boolean debugMessages;
	private Set<SBlock> blocksBroken = new HashSet<SBlock>();
	private FileConfiguration blocks;
	private FileConfiguration f;
	private File bf;
	private File blocksFile; 
	
	public DataAccessor(GriefSaveAPI plugin){
		this.plugin = plugin;
		this.bf = new File(this.plugin.getDataFolder().toString()+"/data");
		this.blocksFile = new File(bf, "blocks.yml");
	}
	
	public void initializeConfig() {
		banList = new ArrayList<String>();
		banListConverted = new HashSet<Material>();
		File file = new File(plugin.getDataFolder(), "config.yml");
		if(!file.exists()) {
			plugin.saveDefaultConfig();
	    }
	    f = YamlConfiguration.loadConfiguration(file);
	    this.useListeners = f.getBoolean("useListeners");
	    this.debugMessages = f.getBoolean("debugMessages");
	    this.banList = f.getStringList("blacklist");
	    for(Material m : convertBanList(banList)){
	    	this.banListConverted.add(m);
	    }
	    plugin.setBanList(banListConverted);
	}
	
	public void loadData(){
		bf = new File(plugin.getDataFolder().toString() + "/data");
		blocksFile = new File(bf, "blocks.yml");  
		if (bf.exists()) {
		    bf.mkdir();
		}
		if (!blocksFile.exists()) {
		    plugin.saveResource("data/blocks.yml", false);
		}
		blocks = YamlConfiguration.loadConfiguration(blocksFile);
		if(blocks!=null){
			String listSerial = blocks.getString("blocks");
			try {
				blocksBroken = BlockSerialization.fromBase64(listSerial);
				plugin.setBlocksBroken(blocksBroken);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void saveData(){
		if(plugin.getBlocksBroken()!=null){
			String listSerial = BlockSerialization.toBase64(plugin.getBlocksBroken());
			blocks.set("blocks", listSerial);
		}
		if (!bf.exists()){
			bf.mkdir();
	    }
		if(blocksFile.exists())
			blocksFile.delete();
		try {
			blocks.save(blocksFile);
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		if (!blocksFile.exists()){
			try {
				blocksFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Set<Material> convertBanList(List<String> banList2){
		Set<Material> newBanList = new HashSet<Material>();
		if(!banList2.equals(null)){
			for(String s : banList2){
				Material mat = Material.valueOf(s);
				newBanList.add(mat);
			}
		}
		return newBanList;
	}
}