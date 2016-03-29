package me.drkmatr1984.GriefSaveAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.drkmatr1984.GriefSaveAPI.utils.BlockSerialization;
import me.drkmatr1984.GriefSaveAPI.utils.SBlock;

public class DataAccessor{
	
	private GriefSaveAPI plugin;
	private List<String> banList = new ArrayList<String>();
	private Set<Material> banListConverted = new HashSet<Material>();
	public boolean useListeners;
	public boolean debugMessages;
	public boolean recordBlockPlaced;
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
	    this.recordBlockPlaced = f.getBoolean("recordBlockPlace");
	    this.banList = f.getStringList("blacklist");
	    for(Material m : convertBanList(banList)){
	    	this.banListConverted.add(m);
	    }
	    plugin.setBanList(banListConverted);
	}
	
	public void loadData(){
		String listSerial = "";
		int size;
		bf = new File(plugin.getDataFolder().toString() + "/data");
		blocksFile = new File(bf, "blocks.yml");  
		if (!bf.exists()) {
			Bukkit.getServer().getLogger().info("Directory Doesn't Exist, Creating...");
		    bf.mkdir();
		}
		if (!blocksFile.exists()) {
			Bukkit.getServer().getLogger().info("Default blockdata file Doesn't Exist, Creating...");
		    plugin.saveResource("data/blocks.yml", false);
		}else{
			blocks = YamlConfiguration.loadConfiguration(blocksFile);
			if(blocks!=null){
				if(!(blocks.getString("blocks") == "") && (blocks.getString("blocks") != null)){
					listSerial = blocks.getString("blocks");
					size = blocks.getInt("size");
					try {
						if(!listSerial.equals("") && !listSerial.equals(null)){
							blocksBroken = BlockSerialization.fromBase64(listSerial, size);
							plugin.setBlocksBroken(blocksBroken);
						}			
					} catch (IOException e) {
						Bukkit.getServer().getLogger().info("Can't load BlocksFile");
						e.printStackTrace();
					}
				}		
			}
		}	
	}
	
	public void saveData(){
		int size;
		blocks = YamlConfiguration.loadConfiguration(blocksFile);
		if(plugin.getBlocksBroken().isEmpty()){
			size = 0;
		}else{
			size = plugin.getBlocksBroken().size();
		}
		if(plugin.getBlocksBroken()!=null){
			String listSerial = BlockSerialization.toBase64(plugin.getBlocksBroken());
			blocks.set("blocks", listSerial);
			blocks.set("size", size);
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