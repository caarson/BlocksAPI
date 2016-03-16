package me.drkmatr1984.GriefSaveAPI;

import java.io.Serializable;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class SBlock implements Serializable{
	
	private static final long serialVersionUID = -5944092517430475805L;
	
	public final String world ;
	public final String mat;
	public final String ent;
	public final int x;
	public final int y;
	public final int z;
	public final byte data;
	
	@SuppressWarnings("deprecation")
	public SBlock(Block block, Entity entity){
		world = block.getLocation().getWorld().toString();
		mat = block.getType().name().toString();
		x = block.getLocation().getBlockX();
		y = block.getLocation().getBlockY();
		z = block.getLocation().getBlockZ();
		data = block.getData();
		ent = entity.getName();
	}
	
	@SuppressWarnings("deprecation")
	public SBlock(Block block){
		world = block.getLocation().getWorld().toString();
		mat = block.getType().name().toString();
		x = block.getLocation().getBlockX();
		y = block.getLocation().getBlockY();
		z = block.getLocation().getBlockZ();
		data = block.getData();
		ent = "";
	}
}