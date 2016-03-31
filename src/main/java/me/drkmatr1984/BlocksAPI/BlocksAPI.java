package me.drkmatr1984.BlocksAPI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.drkmatr1984.BlocksAPI.listeners.BlockEventListeners;
import me.drkmatr1984.BlocksAPI.utils.SBlock;

public class BlocksAPI extends JavaPlugin
{
  private static BlocksAPI plugin;
  private Logger log;
  private PluginManager pm; //Used to register Events
  private Set<SBlock> blocksBroken = new HashSet<SBlock>();
  private Set<Material> banList = new HashSet<Material>();
  public List<String> worldBanList = new ArrayList<String>();
  public boolean recordBlockBreak;
  public boolean recordBlockPlaced; // create config that asks if they would also like to record blockPlaces
  public boolean recordEntityExplode;
  public boolean recordBlockExplode;
  public boolean recordBlockBurn;
  public boolean recordBlockIgnite;
  public boolean recordBlockFromTo;
  public boolean useListeners;
  public boolean debugMessages;
  private DataAccessor data; 
  
  @Override
  public void onEnable()
  {
	plugin = this; //creates a plugin instance for easy access to plugin
	this.log = getLogger();
	this.pm = getServer().getPluginManager();
	this.data = new DataAccessor(this);
	this.data.initializeConfig();
	this.data.loadData();
	this.useListeners = this.data.useListeners;
	this.debugMessages = this.data.debugMessages;
	this.recordBlockBreak = this.data.recordBlockBreak;
	this.recordBlockPlaced = this.data.recordBlockPlaced;
	this.recordEntityExplode = this.data.recordEntityExplode;
	this.recordBlockExplode = this.data.recordBlockExplode;
	this.recordBlockBurn = this.data.recordBlockBurn;
	this.recordBlockIgnite = this.data.recordBlockPlaced;
	this.recordBlockFromTo = this.data.recordBlockFromTo;
	this.worldBanList = this.data.worldBanList;
	getCommand("rollback").setExecutor(new BlocksAPICommands(plugin));
	if(useListeners){
		pm.registerEvents(new BlockEventListeners(plugin, this.blocksBroken, banList), plugin);
	}	
    this.log.info("GriefSaveAPI enabled!");
  }
  
  public void onDisable()
  {
	  this.data.saveData();
  }
  
  public void setBlocksBroken(Set<SBlock> blocksBroken){
	  this.blocksBroken = new HashSet<SBlock>();
	  for(SBlock block : blocksBroken){
		  if(!this.banList.contains(block.getType())){
			  this.blocksBroken.add(block);		  
		  }
	  }
  }
  
  /*
   *  Returns a Set<org.bukkit.Block> representative of all the blocks altered by destruction
   * */
  
  public Set<SBlock> getBlocksBroken(){
	  return this.blocksBroken;
  }
  
  public boolean addToList(SBlock sb){
	  if(!this.banList.contains(sb.getType())){
		  return this.blocksBroken.add(sb);
	  }
	  return false;
  }
  
  public boolean addToList(Block b){
	  if(!this.banList.contains(b.getType())){
		  return this.blocksBroken.add(new SBlock(b));
	  }
	  return false;  
  }
  
  public boolean addToList(Location loc){
	  if(!this.banList.contains(loc.getBlock().getType())){
		  return this.blocksBroken.add(new SBlock(loc));
	  }
	  return false;  
  }
  
  public boolean removeFromList(SBlock sb){
	  return this.blocksBroken.remove(sb);
  }
  
  public boolean removeFromList(Block b){
	  return this.blocksBroken.remove(new SBlock(b));
  }
  
  public boolean removeFromList(Location loc){
	  return this.blocksBroken.remove(new SBlock(loc));
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
		  //if(blockLocation.type.equals("block")){
			  if(blockLocation.mat.equals(bl.mat) && blockLocation.x == bl.x && blockLocation.y == bl.y && blockLocation.z == bl.z){
				  return true;
			  }
		  //} 
		  /*if(blockLocation.type.equals("itemframe")){
			  if(blockLocation.x == bl.x && blockLocation.y == bl.y && blockLocation.z == bl.z){
				  return true;
			  }
		  }*/
	  }
	  return false;
  }
  
  public SBlock getStoredSBlock(Block block){
	  for(SBlock blockLocation : this.blocksBroken){
		  if(blockLocation.mat.equals(block.getType().name().toString()) && blockLocation.x == block.getX() && blockLocation.y == block.getY() && blockLocation.z == block.getZ()){
			  return blockLocation;
		  } 
	  }
	  return null;
  }
  
  public static BlocksAPI getInstance(){
	  return plugin;
  }
}