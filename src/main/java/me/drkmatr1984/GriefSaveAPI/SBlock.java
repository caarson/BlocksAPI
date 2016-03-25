package me.drkmatr1984.GriefSaveAPI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.material.Door;

public class SBlock implements Serializable{
	
	private static final long serialVersionUID = -5944092517430475805L;
	
	public final String world;
	public final String mat;
	public final String ent;
	public final int x;
	public final int y;
	public final int z;
	public final byte data;
	//Info for Storing Doors and their movements
	public String doorTopWorld;
	public String doorTopMat;
	public int doorTopX;
	public int doorTopY;
	public int doorTopZ;
	public byte doorTopData;
	public String doorBotWorld;
	public String doorBotMat;
	public int doorBotX;
	public int doorBotY;
	public int doorBotZ;
	public byte doorBotData;
	//Info for Storing Signs
	public List<String> signLines;
	//Info for Storing Spawners
	public String entityType;
	public int delay;
	
	@SuppressWarnings("deprecation")
	public SBlock(Block block, Entity entity){
		world = block.getLocation().getWorld().getName().toString();		
		x = block.getLocation().getBlockX();
		y = block.getLocation().getBlockY();
		z = block.getLocation().getBlockZ();
		mat = block.getType().name().toString();
		data = block.getData();
		ent = entity.getName();
		if(block.getState().getData() instanceof Door){
			Door door = (Door) block.getState().getData();
			Block topHalf;
			Block bottomHalf;
			if (door.isTopHalf()) {
				topHalf = block.getState().getBlock();
				bottomHalf = block.getState().getBlock().getRelative(BlockFace.DOWN);
			} else {
				bottomHalf = block.getState().getBlock();
				topHalf = block.getState().getBlock().getRelative(BlockFace.UP);
			}
			doorTopWorld = topHalf.getLocation().getWorld().getName().toString();		
			doorTopX = topHalf.getLocation().getBlockX();
			doorTopY = topHalf.getLocation().getBlockY();
			doorTopZ = topHalf.getLocation().getBlockZ();
			doorTopMat = topHalf.getType().name().toString();
			doorTopData = topHalf.getData();
			doorBotWorld = bottomHalf.getLocation().getWorld().getName().toString();		
			doorBotX = bottomHalf.getLocation().getBlockX();
			doorBotY = bottomHalf.getLocation().getBlockY();
			doorBotZ = bottomHalf.getLocation().getBlockZ();
			doorBotMat = bottomHalf.getType().name().toString();
			doorBotData = bottomHalf.getData();			
		}
		if(block.getState() instanceof Sign){
			Sign sign = (Sign)(block.getState());
			signLines = new ArrayList<String>();
			if(!sign.getLines().equals(null)){
				for (String line : sign.getLines()){
					signLines.add(line);
				}
			}			
		}
		if(block.getState().getData() instanceof CreatureSpawner){
			CreatureSpawner spawner = (CreatureSpawner) block.getState().getData();
			entityType = spawner.getCreatureTypeName();
			delay = spawner.getDelay();
		}
		/*try {
			BlockState state;
			BlockState altState;
			Block block1 = blockStatedata.getBlock();
			
			} else if (blockStatedata instanceof CreatureSpawner) {

				block.setTypeIdAndData(blockStatedata.getTypeId(), blockStatedata.getData().getData(), false);
				((CreatureSpawner) block.getState()).setSpawnedType(((CreatureSpawner) blockStatedata).getSpawnedType());

			} else if (blockStatedata instanceof InventoryHolder) {

				block.setTypeId(blockStatedata.getTypeId(), false);

				// Container to receive the inventory
				Inventory container = ((InventoryHolder) block.getState()).getInventory();
				container.setContents(contents.toArray(new ItemStack[0]));
				
				block.setData(blockStatedata.getData().getData(), false);

			} else if (blockStatedata.getData() instanceof PistonExtensionMaterial) {

				PistonExtensionMaterial extension = (PistonExtensionMaterial) blockStatedata.getData();
				Block piston = block.getRelative(extension.getAttachedFace());
				block.setTypeIdAndData(blockStatedata.getTypeId(), blockStatedata.getData().getData(), false);
				if (altState != null) {
					piston.setTypeIdAndData(altState.getTypeId(), altState.getData().getData(), false);
				}
			} else if (state.getData() instanceof Attachable) {

				Block attachedBlock = block.getRelative(((Attachable) state.getData()).getAttachedFace());
				if (attachedBlock.getTypeId() == 0) {
					attachedBlock.setTypeId(placeholder.getId(), false);
					TownyRegenAPI.addPlaceholder(attachedBlock);
				}
				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);

			} else {

				if (NeedsPlaceholder.contains(state.getType())) {
					Block blockBelow = block.getRelative(BlockFace.DOWN);
					if (blockBelow.getTypeId() == 0) {
						if (state.getType().equals(Material.CROPS)) {
							blockBelow.setTypeId(Material.SOIL.getId(), true);
						} else {
							blockBelow.setTypeId(placeholder.getId(), true);
						}
						TownyRegenAPI.addPlaceholder(blockBelow);
					}
				}
				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), !NeedsPlaceholder.contains(state.getType()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/ 
	}
	
	
	@SuppressWarnings("deprecation")
	public SBlock(Block block){
		world = block.getLocation().getWorld().getName().toString();		
		x = block.getLocation().getBlockX();
		y = block.getLocation().getBlockY();
		z = block.getLocation().getBlockZ();
		mat = block.getType().name().toString();
		data = block.getData();
		ent = "";
		if(block.getState().getData() instanceof Door){
			Door door = (Door) block.getState().getData();
			Block topHalf;
			Block bottomHalf;
			if (door.isTopHalf()) {
				topHalf = block.getState().getBlock();
				bottomHalf = block.getState().getBlock().getRelative(BlockFace.DOWN);
			} else {
				bottomHalf = block.getState().getBlock();
				topHalf = block.getState().getBlock().getRelative(BlockFace.UP);
			}
			doorTopWorld = topHalf.getLocation().getWorld().getName().toString();		
			doorTopX = topHalf.getLocation().getBlockX();
			doorTopY = topHalf.getLocation().getBlockY();
			doorTopZ = topHalf.getLocation().getBlockZ();
			doorTopMat = topHalf.getType().name().toString();
			doorTopData = topHalf.getData();
			doorBotWorld = bottomHalf.getLocation().getWorld().getName().toString();		
			doorBotX = bottomHalf.getLocation().getBlockX();
			doorBotY = bottomHalf.getLocation().getBlockY();
			doorBotZ = bottomHalf.getLocation().getBlockZ();
			doorBotMat = bottomHalf.getType().name().toString();
			doorBotData = bottomHalf.getData();			
		}
		if(block.getState() instanceof Sign){
			Sign sign = (Sign)(block.getState());
			signLines = new ArrayList<String>();
			if(!sign.getLines().equals(null)){
				for (String line : sign.getLines()){
					signLines.add(line);
				}
			}			
		}
	}
}