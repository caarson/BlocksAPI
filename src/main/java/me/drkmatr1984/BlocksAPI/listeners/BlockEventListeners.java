package me.drkmatr1984.BlocksAPI.listeners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
//import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.material.Attachable;

import me.drkmatr1984.BlocksAPI.BlocksAPI;
import me.drkmatr1984.BlocksAPI.utils.SBlock;
import me.drkmatr1984.BlocksAPI.utils.Utils;

public class BlockEventListeners implements Listener{
	
	private Set<Material> banList = new HashSet<Material>();
	private BlocksAPI plugin;
	
	public BlockEventListeners(BlocksAPI plugin,Set<SBlock> blocksBroken, Set<Material> banList){
		this.plugin = plugin;
		this.banList = banList;
	}
	
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		if(plugin.recordBlockBreak){
			Block block = event.getBlock();
			ArrayList<SBlock> sBlocks = new ArrayList<SBlock>();
			Entity entity = (Entity) event.getPlayer();
			if(plugin.worldBanList.contains(block.getWorld().getName().toString())){
				return;
			}
			if(this.banList.contains(block.getType())){
				return;
			}
			for(BlockFace face : BlockFace.values()){
				if(!face.equals(BlockFace.SELF)){
					if((block.getRelative(face)).getState().getData() instanceof Attachable || (block.getRelative(face)).getType().equals(Material.VINE) || (block.getRelative(face)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(face)).getType().equals(Material.CHORUS_FLOWER)){
						if(entity!=null){
							sBlocks.add(new SBlock((block.getRelative(face)), entity));
						}else{
							sBlocks.add(new SBlock((block.getRelative(face))));
						}
					}
				}
			}
			if(Utils.isOtherAttachable((block.getRelative(BlockFace.UP)).getType())){
				if(entity!=null){
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP)), entity));
				}else{
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP))));
				}
			}
			if((block.getRelative(BlockFace.UP)).getType().equals(Material.CACTUS) || (block.getRelative(BlockFace.UP)).getType().equals(Material.SUGAR_CANE_BLOCK) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_FLOWER)){
				Block up = block.getRelative(BlockFace.UP);
				do
				{
					if(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER)){
						if(entity!=null){
							sBlocks.add(new SBlock(up, entity));
						}else{
							sBlocks.add(new SBlock(up));
						}
					}
					up = ((up.getLocation()).add(0,1,0)).getBlock();
				}while(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER));
			}
			if(entity!=null){
				sBlocks.add(new SBlock(block, entity));
			}else{
				sBlocks.add(new SBlock(block));
			}
			for(SBlock bL : sBlocks){
				if(bL!=null && !plugin.containsBlockLocation(bL)){
					if(!plugin.addToList(bL)){
						Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot add to List");
					}
					if(plugin.debugMessages){
						Bukkit.getServer().getLogger().info("BlockBreakEvent");
						Bukkit.getServer().getLogger().info("Saved BlockLocation");
						Bukkit.getServer().getLogger().info("Location : " + "X:"+ bL.x + ", " + "Y:"+ bL.y + ", " + "Z:"+ bL.z);
						Bukkit.getServer().getLogger().info("BlockType : " + bL.mat);
						Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
						if(block.getState() instanceof Skull){
							Bukkit.getServer().getLogger().info("SkullType: " + bL.skullType);
							Bukkit.getServer().getLogger().info("SkullOwner: " + bL.skullOwner);
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		if(plugin.recordEntityExplode){
			List<Block> blocks = event.blockList();
			Entity entity = event.getEntity();
			for(Block block : blocks){
				ArrayList<SBlock> sBlocks = new ArrayList<SBlock>();
				if(plugin.worldBanList.contains(block.getWorld().getName().toString())){
					return;
				}
				if(this.banList.contains(block.getType())){
					return;
				}
				for(BlockFace face : BlockFace.values()){
					if(!face.equals(BlockFace.SELF)){
						if((block.getRelative(face)).getState().getData() instanceof Attachable || (block.getRelative(face)).getType().equals(Material.VINE) || (block.getRelative(face)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(face)).getType().equals(Material.CHORUS_FLOWER)){
							if(entity!=null){
								sBlocks.add(new SBlock((block.getRelative(face)), entity));
							}else{
								sBlocks.add(new SBlock((block.getRelative(face))));
							}
						}
					}
				}
				if(Utils.isOtherAttachable((block.getRelative(BlockFace.UP)).getType())){
					if(entity!=null){
						sBlocks.add(new SBlock((block.getRelative(BlockFace.UP)), entity));
					}else{
						sBlocks.add(new SBlock((block.getRelative(BlockFace.UP))));
					}
				}
				if((block.getRelative(BlockFace.UP)).getType().equals(Material.CACTUS) || (block.getRelative(BlockFace.UP)).getType().equals(Material.SUGAR_CANE_BLOCK) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_FLOWER)){
					Block up = block.getRelative(BlockFace.UP);
					do
					{
						if(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER)){
							if(entity!=null){
								sBlocks.add(new SBlock(up, entity));
							}else{
								sBlocks.add(new SBlock(up));
							}
						}
						up = ((up.getLocation()).add(0,1,0)).getBlock();
					}while(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER));
				}
				if(entity!=null){
					sBlocks.add(new SBlock(block, entity));
				}else{
					sBlocks.add(new SBlock(block));
				}
				for(SBlock bL : sBlocks){
					if(bL!=null && !plugin.containsBlockLocation(bL)){
						if(!plugin.addToList(bL)){
							Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot add to List");
						}
						if(plugin.debugMessages){
							Bukkit.getServer().getLogger().info("EntityExplodeEvent");
							Bukkit.getServer().getLogger().info("Saved BlockLocation");
							Bukkit.getServer().getLogger().info("Location : " + "X:"+ bL.x + ", " + "Y:"+ bL.y + ", " + "Z:"+ bL.z);
							Bukkit.getServer().getLogger().info("BlockType : " + bL.mat);
							Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
							if(block.getState() instanceof Skull){
								Bukkit.getServer().getLogger().info("SkullType: " + bL.skullType);
								Bukkit.getServer().getLogger().info("SkullOwner: " + bL.skullOwner);
							}
						}
					}
				}
			}
		}	
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockExplode(BlockExplodeEvent event) {
		if(plugin.recordBlockExplode){
			List<Block> blocks = event.blockList();
			for(Block block : blocks){
				ArrayList<SBlock> sBlocks = new ArrayList<SBlock>();
				if(plugin.worldBanList.contains(block.getWorld().getName().toString())){
					return;
				}
				if(this.banList.contains(block.getType())){
					return;
				}
				for(BlockFace face : BlockFace.values()){
					if(!face.equals(BlockFace.SELF)){
						if((block.getRelative(face)).getState().getData() instanceof Attachable || (block.getRelative(face)).getType().equals(Material.VINE) || (block.getRelative(face)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(face)).getType().equals(Material.CHORUS_FLOWER)){
							sBlocks.add(new SBlock((block.getRelative(face))));
						}
					}
				}
				if(Utils.isOtherAttachable((block.getRelative(BlockFace.UP)).getType())){
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP))));
				}
				if((block.getRelative(BlockFace.UP)).getType().equals(Material.CACTUS) || (block.getRelative(BlockFace.UP)).getType().equals(Material.SUGAR_CANE_BLOCK) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_FLOWER)){
					Block up = block.getRelative(BlockFace.UP);
					do
					{
						if(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER)){
							sBlocks.add(new SBlock(up));
						}
						up = ((up.getLocation()).add(0,1,0)).getBlock();
					}while(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER));
				}
				sBlocks.add(new SBlock(block));
				for(SBlock bL : sBlocks){
					if(bL!=null && !plugin.containsBlockLocation(bL)){
						if(!plugin.addToList(bL)){
							Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot add to List");
						}
						if(plugin.debugMessages){
							Bukkit.getServer().getLogger().info("BlockExplodeEvent");
							Bukkit.getServer().getLogger().info("Saved BlockLocation");
							Bukkit.getServer().getLogger().info("Location : " + "X:"+ bL.x + ", " + "Y:"+ bL.y + ", " + "Z:"+ bL.z);
							Bukkit.getServer().getLogger().info("BlockType : " + bL.mat);
							Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
							if(block.getState() instanceof Skull){
								Bukkit.getServer().getLogger().info("SkullType: " + bL.skullType);
								Bukkit.getServer().getLogger().info("SkullOwner: " + bL.skullOwner);
							}
						}
					}
				}
			}
		}
	}
	
	/*@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
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
				Bukkit.getServer().getLogger().info("BlockDamageEvent");
				Bukkit.getServer().getLogger().info("Saved BlockLocation");
				Bukkit.getServer().getLogger().info("Location : " + "X:"+ location.getX() + ", " + "Y:"+ location.getY() + ", " + "Z:"+ location.getZ());
				Bukkit.getServer().getLogger().info("BlockType : " + block.getType().toString());
				Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
			}
		}
	}*/   // Don't think I need this, but we'll leave it here for now
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBurn(BlockBurnEvent event){
		if(plugin.recordBlockBurn){
			Block block = event.getBlock();
			ArrayList<SBlock> sBlocks = new ArrayList<SBlock>();
			if(plugin.worldBanList.contains(block.getWorld().getName().toString())){
				return;
			}
			if(this.banList.contains(block.getType())){
				return;
			}
			for(BlockFace face : BlockFace.values()){
				if(!face.equals(BlockFace.SELF)){
					if((block.getRelative(face)).getState().getData() instanceof Attachable || (block.getRelative(face)).getType().equals(Material.VINE) || (block.getRelative(face)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(face)).getType().equals(Material.CHORUS_FLOWER)){
						sBlocks.add(new SBlock((block.getRelative(face))));
					}
				}
			}
			if(Utils.isOtherAttachable((block.getRelative(BlockFace.UP)).getType())){
				sBlocks.add(new SBlock((block.getRelative(BlockFace.UP))));
			}
			if((block.getRelative(BlockFace.UP)).getType().equals(Material.CACTUS) || (block.getRelative(BlockFace.UP)).getType().equals(Material.SUGAR_CANE_BLOCK) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_FLOWER)){
				Block up = block.getRelative(BlockFace.UP);
				do
				{
					if(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER)){
						sBlocks.add(new SBlock(up));
					}
					up = ((up.getLocation()).add(0,1,0)).getBlock();
				}while(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER));
			}
			sBlocks.add(new SBlock(block));
			for(SBlock bL : sBlocks){
				if(bL!=null && !plugin.containsBlockLocation(bL)){
					if(!plugin.addToList(bL)){
						Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot add to List");
					}
					if(plugin.debugMessages){
						Bukkit.getServer().getLogger().info("BlockBurnEvent");
						Bukkit.getServer().getLogger().info("Saved BlockLocation");
						Bukkit.getServer().getLogger().info("Location : " + "X:"+ bL.x + ", " + "Y:"+ bL.y + ", " + "Z:"+ bL.z);
						Bukkit.getServer().getLogger().info("BlockType : " + bL.mat);
						Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
						if(block.getState() instanceof Skull){
							Bukkit.getServer().getLogger().info("SkullType: " + bL.skullType);
							Bukkit.getServer().getLogger().info("SkullOwner: " + bL.skullOwner);
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(plugin.recordBlockPlaced){
			Block block = event.getBlock();
			ArrayList<SBlock> sBlocks = new ArrayList<SBlock>();
			Entity entity = (Entity) event.getPlayer();
			if(plugin.worldBanList.contains(block.getWorld().getName().toString())){
				return;
			}
			if(this.banList.contains(block.getType())){
				return;
			}
			for(BlockFace face : BlockFace.values()){
				if(!face.equals(BlockFace.SELF)){
					if((block.getRelative(face)).getState().getData() instanceof Attachable || (block.getRelative(face)).getType().equals(Material.VINE) || (block.getRelative(face)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(face)).getType().equals(Material.CHORUS_FLOWER)){
						if(entity!=null){
							sBlocks.add(new SBlock((block.getRelative(face)), entity));
						}else{
							sBlocks.add(new SBlock((block.getRelative(face))));
						}
					}
				}
			}
			if(Utils.isOtherAttachable((block.getRelative(BlockFace.UP)).getType())){
				if(entity!=null){
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP)), entity));
				}else{
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP))));
				}
			}
			if((block.getRelative(BlockFace.UP)).getType().equals(Material.CACTUS) || (block.getRelative(BlockFace.UP)).getType().equals(Material.SUGAR_CANE_BLOCK) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_FLOWER)){
				Block up = block.getRelative(BlockFace.UP);
				do
				{
					if(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER)){
						if(entity!=null){
							sBlocks.add(new SBlock(up, entity));
						}else{
							sBlocks.add(new SBlock(up));
						}
					}
					up = ((up.getLocation()).add(0,1,0)).getBlock();
				}while(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER));
			}
			if(entity!=null){
				sBlocks.add(new SBlock(block, entity));
			}else{
				sBlocks.add(new SBlock(block));
			}
			for(SBlock bL : sBlocks){
				if(bL!=null && !plugin.containsBlockLocation(bL)){
					if(!plugin.addToList(bL)){
						Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot add to List");
					}
					if(plugin.debugMessages){
						Bukkit.getServer().getLogger().info("BlockPlaceEvent ");
						Bukkit.getServer().getLogger().info("Saved BlockLocation");
						Bukkit.getServer().getLogger().info("Location : " + "X:"+ bL.x + ", " + "Y:"+ bL.y + ", " + "Z:"+ bL.z);
						Bukkit.getServer().getLogger().info("BlockType : " + bL.mat);
						Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
						if(block.getState() instanceof Skull){
							Bukkit.getServer().getLogger().info("SkullType: " + bL.skullType);
							Bukkit.getServer().getLogger().info("SkullOwner: " + bL.skullOwner);
						}
					}
				}
			}
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockIgnite(BlockIgniteEvent event) {
		if(plugin.recordBlockIgnite){
			Block block = event.getBlock();
			ArrayList<SBlock> sBlocks = new ArrayList<SBlock>();
			Entity entity = (Entity) event.getPlayer();
			if(plugin.worldBanList.contains(block.getWorld().getName().toString())){
				return;
			}
			if(this.banList.contains(block.getType())){
				return;
			}
			for(BlockFace face : BlockFace.values()){
				if(!face.equals(BlockFace.SELF)){
					if((block.getRelative(face)).getState().getData() instanceof Attachable || (block.getRelative(face)).getType().equals(Material.VINE) || (block.getRelative(face)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(face)).getType().equals(Material.CHORUS_FLOWER)){
						if(entity!=null){
							sBlocks.add(new SBlock((block.getRelative(face)), entity));
						}else{
							sBlocks.add(new SBlock((block.getRelative(face))));
						}
					}
				}
			}
			if(Utils.isOtherAttachable((block.getRelative(BlockFace.UP)).getType())){
				if(entity!=null){
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP)), entity));
				}else{
					sBlocks.add(new SBlock((block.getRelative(BlockFace.UP))));
				}
			}
			if((block.getRelative(BlockFace.UP)).getType().equals(Material.CACTUS) || (block.getRelative(BlockFace.UP)).getType().equals(Material.SUGAR_CANE_BLOCK) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_PLANT) || (block.getRelative(BlockFace.UP)).getType().equals(Material.CHORUS_FLOWER)){
				Block up = block.getRelative(BlockFace.UP);
				do
				{
					if(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER)){
						if(entity!=null){
							sBlocks.add(new SBlock(up, entity));
						}else{
							sBlocks.add(new SBlock(up));
						}
					}
					up = ((up.getLocation()).add(0,1,0)).getBlock();
				}while(up.getType().equals(Material.CACTUS) || up.getType().equals(Material.SUGAR_CANE_BLOCK) || up.getType().equals(Material.CHORUS_PLANT) || up.getType().equals(Material.CHORUS_FLOWER));
			}
			if(entity!=null){
				sBlocks.add(new SBlock(block, entity));
			}else{
				sBlocks.add(new SBlock(block));
			}
			for(SBlock bL : sBlocks){
				if(bL!=null && !plugin.containsBlockLocation(bL)){
					if(!plugin.addToList(bL)){
						Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "Cannot add to List");
					}
					if(plugin.debugMessages){
						Bukkit.getServer().getLogger().info("BlockIgniteEvent");
						Bukkit.getServer().getLogger().info("Saved BlockLocation");
						Bukkit.getServer().getLogger().info("Location : " + "X:"+ bL.x + ", " + "Y:"+ bL.y + ", " + "Z:"+ bL.z);
						Bukkit.getServer().getLogger().info("BlockType : " + bL.mat);
						Bukkit.getServer().getLogger().info("Entity : " + bL.ent);
						if(block.getState() instanceof Skull){
							Bukkit.getServer().getLogger().info("SkullType: " + bL.skullType);
							Bukkit.getServer().getLogger().info("SkullOwner: " + bL.skullOwner);
						}
					}
				}
			}
		}
	}
}