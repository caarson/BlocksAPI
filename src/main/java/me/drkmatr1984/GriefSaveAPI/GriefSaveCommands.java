package me.drkmatr1984.GriefSaveAPI;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.material.Door;

import me.drkmatr1984.GriefSaveAPI.tasks.DelayedRegenTask;
import me.drkmatr1984.GriefSaveAPI.utils.SBlock;
import me.drkmatr1984.GriefSaveAPI.utils.Utils;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;

@SuppressWarnings({ "deprecation", "unchecked" })
public class GriefSaveCommands implements org.bukkit.command.CommandExecutor
{
	private GriefSaveAPI plugin;	
	
	public GriefSaveCommands(GriefSaveAPI plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
		    if (cmd.getName().equalsIgnoreCase("rollback")) {
		    	ArrayList<SBlock> blocks = (ArrayList<SBlock>) Utils.setToList(plugin.getBlocksBroken());
		    	for(SBlock sb : blocks){
		    		Location l = new Location(Bukkit.getServer().getWorld(sb.world),sb.x,sb.y,sb.z);
		    		Block bl = l.getBlock();
		    		BlockState blockState = bl.getState();
		    		byte bite = sb.data;
		    		Material mat = Material.valueOf(sb.mat);
		    		if(Utils.isOtherAttachable(mat) || mat.equals(Material.CACTUS) || mat.equals(Material.SUGAR_CANE_BLOCK)){
		    			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DelayedRegenTask(bl, mat, bite, blockState, sb) {
		    				  @Override
		    				  public void run() {
		    					  block.setTypeIdAndData(mat.getId(), bite, true);
		    					  boolean error = setExtraData(blockState, sb, mat);
		    					  if(!error && plugin.debugMessages){
		    						  Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "An Error has Occured");
		    					  }
		    				  }
		    			}, 2L);
		    		}else{
		    			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DelayedRegenTask(bl, mat, bite, blockState, sb) {
		    				  @Override
		    				  public void run() {
		    					  block.setTypeIdAndData(mat.getId(), bite, true);
		    					  boolean error = setExtraData(blockState, sb, mat);
		    					  if(!error && plugin.debugMessages){
		    						  Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "An Error has Occured");
		    					  }
		    				  }
		    			}, 1L);
		    		}		    		
		    		if(!plugin.removeFromList(sb) && plugin.debugMessages){
		    			Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot remove from List");
		    		}
		    	}		    		
		    }
		}catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(ChatColor.DARK_RED + "An Error has Occured");
	    }
		return false;
	}
	
	public boolean setExtraData(BlockState blockState, SBlock sb, Material mat){
		if(blockState.getData() instanceof Door){
			Door door = (Door) blockState.getData(); 
	    	Location topLoc = new Location(Bukkit.getServer().getWorld(sb.doorTopWorld),sb.doorTopX,sb.doorTopY,sb.doorTopZ);
		   	Location botLoc = new Location(Bukkit.getServer().getWorld(sb.doorBotWorld),sb.doorBotX,sb.doorBotY,sb.doorBotZ);
	    	Block topHalf = topLoc.getBlock();
		   	Block bottomHalf = botLoc.getBlock();
	    	door.setTopHalf(true);
			topHalf.setTypeIdAndData(mat.getId(), sb.doorTopData, false);
			door.setTopHalf(false);
			bottomHalf.setTypeIdAndData(mat.getId(), sb.doorBotData, false);
			return true;
		}
		if(blockState instanceof Sign){
			if(!sb.signLines.isEmpty() && !sb.signLines.equals(null)){
				Sign sign = (Sign) blockState;
				int i = 0;
				for (String line : sb.signLines){
					if(!line.equals(null) || !line.equals(""))
						sign.setLine(i++, line);
				}
				sign.update(true);
			}
			return true;
		}
		if(blockState instanceof CreatureSpawner){
			CreatureSpawner spawner = (CreatureSpawner) blockState;
			spawner.setCreatureTypeByName(sb.entityType);
			spawner.setDelay(sb.delay);
			return true;
		}
		if(blockState instanceof Skull){
			Skull skull = (Skull) blockState;
			skull.setSkullType(SkullType.valueOf(sb.skullType));
			if(!sb.skullOwner.equals("") && !sb.skullOwner.equals(null) && sb.skullType.equalsIgnoreCase("player")){
				skull.setOwner(sb.skullOwner);
				if((skull.getOwner().toString().toLowerCase()).equals("cscorelib") && !sb.customTexture.equals(null) && !sb.customTexture.equals("")){
					try {
						CustomSkull.setSkull(blockState.getBlock(), sb.customTexture);
					} catch (Exception e) {				
						e.printStackTrace();
						return false;
					}
				}else{
					skull.update();
				}
				return true;
			}
		}
		return true;
	}
}