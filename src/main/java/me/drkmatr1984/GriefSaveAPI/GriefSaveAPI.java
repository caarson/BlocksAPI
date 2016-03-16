package me.drkmatr1984.GriefSaveAPI;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;

import me.drkmatr1984.GriefSaveAPI.listeners.BlockEventListeners;

public class GriefSaveAPI extends org.bukkit.plugin.java.JavaPlugin
{
  public GriefSaveAPI plugin;
  private Logger log = getLogger();
  private PluginManager pm = getServer().getPluginManager(); //Used to register Events
  private Set<SBlock> blocksBroken = new HashSet<SBlock>();
  private Set<Material> banList = new HashSet<Material>();
  public boolean recordBlockPlaced; // create config that asks if they would also like to record blockPlaces
  public boolean useListeners;
  public boolean debugMessages;
  private DataAccessor data; 
  
  public void onEnable()
  {
	plugin = this; //creates a plugin instance for easy access to plugin
	this.data = new DataAccessor(this);
	this.data.initializeConfig();
	this.data.loadData();
	this.useListeners = this.data.useListeners;
	this.debugMessages = this.data.debugMessages;
	if(useListeners){
		pm.registerEvents(new BlockEventListeners(plugin, this.blocksBroken, banList), plugin);
	}	
    this.log.info("GriefSaveAPI enabled!");
  }
  
  public void onDisable()
  {
	  this.data.saveData();
  }
  /*
   *  A method to add blocks to block save list manually. First get the Set<Block> with 
   *  Set<Block> blocks = getBlocksBroken();, then add the block to the list, then use this method to reset 
   *  the list within the API
   * */
  
  public GriefSaveAPI(boolean useListeners, boolean debugMessages){
	  plugin = this; //creates a plugin instance for easy access to plugin
	  this.data.loadData();
	  this.useListeners = this.data.useListeners;
	  this.debugMessages = this.data.debugMessages;
  }
  
  public void setBlocksBroken(Set<SBlock> blocksBroken){
	  this.blocksBroken = blocksBroken;
  }
  
  /*
   *  Returns a Set<org.bukkit.Block> representative of all the blocks altered by warlike activites
   * */
  
  public Set<SBlock> getBlocksBroken(){
	  return this.blocksBroken;
  }
  
  public void setBanList(Set<Material> banList){
	  this.banList = banList;
  }
  
  public Set<Material> getBanList(){
	  return this.banList;
  }
  
  /*
   *  Returns true if the block types and locations match, ignoring entities in the BlockLocation, false if they do not 
   * */
  public boolean containsBlockLocation(SBlock bl){
	  for(SBlock blockLocation : this.blocksBroken){
		  if(blockLocation.mat == bl.mat && blockLocation.x == bl.x && blockLocation.y == bl.y && blockLocation.z == bl.z){
			  return true;
		  }
	  }
	  return false;
  }
  
}