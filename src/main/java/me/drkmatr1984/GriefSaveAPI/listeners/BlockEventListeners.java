package me.drkmatr1984.GriefSaveAPI.listeners;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.drkmatr1984.GriefSaveAPI.SBlock;
import me.drkmatr1984.GriefSaveAPI.GriefSaveAPI;

public class BlockEventListeners implements Listener{
	
	private Set<SBlock> blocksBroken = new HashSet<SBlock>();
	private Set<Material> banList = new HashSet<Material>();
	private GriefSaveAPI plugin;
	
	public BlockEventListeners(GriefSaveAPI plugin,Set<SBlock> blocksBroken, Set<Material> banList){
		this.blocksBroken = blocksBroken;
		this.plugin = plugin;
		this.banList = banList;
	}
	
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){		
		Block block = event.getBlock();
		Location location = block.getLocation();
		Player player = event.getPlayer();		
		SBlock bL;
		if((player!=null) && (!(this.banList.contains(block.getType())))){ 
			bL = new SBlock(block, player);
		}else{
			bL = new SBlock(block);
		}
		if(bL!=null && !plugin.containsBlockLocation(bL)){
			blocksBroken.add(bL);
			plugin.setBlocksBroken(blocksBroken);
			if(plugin.debugMessages){
				Bukkit.getServer().getLogger().info("Saved BlockLocation");
				Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
				Bukkit.getServer().getLogger().info("BlockType : " + block.getType().toString());
				Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		List<Block> blocks = event.blockList();
		Entity e = event.getEntity();
		SBlock bL;
		for(Block b : blocks){
			if(!(banList.contains(b.getType()))){
				if(e!=null){
					bL = new SBlock(b, e);
				}else{
					bL = new SBlock(b);
				}
				if(bL!=null && !plugin.containsBlockLocation(bL)){
					blocksBroken.add(bL);
					plugin.setBlocksBroken(blocksBroken);
					if(plugin.debugMessages){
						Bukkit.getServer().getLogger().info("Saved BlockLocation");
						Location location = b.getLocation();
						Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
						Bukkit.getServer().getLogger().info("BlockType : " + b.getType().toString());
						Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
					}
				}
			}
		}	
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockExplode(BlockExplodeEvent event) {
		List<Block> blocks = event.blockList();
		SBlock bL;
		for(Block b : blocks){
			if(!(banList.contains(b.getType()))){
				bL = new SBlock(b);
				if(bL!=null && !plugin.containsBlockLocation(bL)){
					blocksBroken.add(bL);
					plugin.setBlocksBroken(blocksBroken);
					if(plugin.debugMessages){
						Bukkit.getServer().getLogger().info("Saved BlockLocation");
						Location location = b.getLocation();
						Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
						Bukkit.getServer().getLogger().info("BlockType : " + b.getType().toString());
						Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
					}
				}
			}
		}	
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockDamage(BlockDamageEvent event){
		Block block = event.getBlock();
		Location location = block.getLocation();
		Player player = event.getPlayer();		
		SBlock bL;
		if((player!=null) && (!(this.banList.contains(block.getType())))){ 
			bL = new SBlock(block, (Entity)player);
		}else{
			bL = new SBlock(block);
		}
		if(bL!=null && !plugin.containsBlockLocation(bL)){
			blocksBroken.add(bL);
			plugin.setBlocksBroken(blocksBroken);
			if(plugin.debugMessages){
				Bukkit.getServer().getLogger().info("Saved BlockLocation");
				Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
				Bukkit.getServer().getLogger().info("BlockType : " + block.getType().toString());
				Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBurn(BlockBurnEvent event){
		Block block = event.getBlock();
		Location location = block.getLocation();		
		SBlock bL;
		bL = new SBlock(block);
		if(bL!=null && !plugin.containsBlockLocation(bL)){
			blocksBroken.add(bL);
			plugin.setBlocksBroken(blocksBroken);
			if(plugin.debugMessages){
				Bukkit.getServer().getLogger().info("Saved BlockLocation");
				Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
				Bukkit.getServer().getLogger().info("BlockType : " + block.getType().toString());
				Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(plugin.recordBlockPlaced){
			Block block = event.getBlock();
			Location location = block.getLocation();
			Player player = event.getPlayer();		
			SBlock bL;
			if((player!=null) && (!(this.banList.contains(block.getType())))){ 
				bL = new SBlock(block, player);
			}else{
				bL = new SBlock(block);
			}
			if(bL!=null && !plugin.containsBlockLocation(bL)){
				blocksBroken.add(bL);
				plugin.setBlocksBroken(blocksBroken);
				if(plugin.debugMessages){
					Bukkit.getServer().getLogger().info("Saved BlockLocation");
					Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
					Bukkit.getServer().getLogger().info("BlockType : " + block.getType().toString());
					Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockIgnite(BlockIgniteEvent event) {
		Block block = event.getBlock();
		Location location = block.getLocation();
		Player player = event.getPlayer();		
		SBlock bL;
		if((player!=null) && (!(this.banList.contains(block.getType())))){ 
			bL = new SBlock(block, player);
		}else{
			bL = new SBlock(block);
		}
		if(bL!=null && !plugin.containsBlockLocation(bL)){
			blocksBroken.add(bL);
			plugin.setBlocksBroken(blocksBroken);
			if(plugin.debugMessages){
				Bukkit.getServer().getLogger().info("Saved BlockLocation");
				Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
				Bukkit.getServer().getLogger().info("BlockType : " + block.getType().toString());
				Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
			}
		}
	}
}