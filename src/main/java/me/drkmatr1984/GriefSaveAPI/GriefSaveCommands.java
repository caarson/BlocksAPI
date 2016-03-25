package me.drkmatr1984.GriefSaveAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.material.Door;

public class GriefSaveCommands implements org.bukkit.command.CommandExecutor
{
	private GriefSaveAPI plugin;	
	
	public GriefSaveCommands(GriefSaveAPI plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
		    if (cmd.getName().equalsIgnoreCase("rollback")) {
		    	for(SBlock sb : plugin.getBlocksBroken()){
		    		Location l = new Location(Bukkit.getServer().getWorld(sb.world),sb.x,sb.y,sb.z);
		    		Block bl = l.getBlock();
		    		byte bite = sb.data;
		    		Material mat = Material.valueOf(sb.mat);
		    		bl.setTypeIdAndData(mat.getId(), bite, true);
		    		BlockState blockState = bl.getState();
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
		    		}
		    		if(blockState instanceof Sign){
		    			Sign sign = (Sign) blockState;
						int i = 0;
						for (String line : sb.signLines){
							if(!line.equals(null) || !line.equals(""))
								sign.setLine(i++, line);
						}				
						sign.update(true);
		    		}
		    		if(blockState.getData() instanceof CreatureSpawner){
		    			CreatureSpawner spawner = (CreatureSpawner) blockState;
		    			spawner.setCreatureTypeByName(sb.entityType);
		    			spawner.setDelay(sb.delay);
		    		}
		    	}		    		
		    }
		}catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(ChatColor.DARK_RED + "An Error has Occured");
	    }
		return false;
	}
}