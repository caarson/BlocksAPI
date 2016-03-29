package me.drkmatr1984.GriefSaveAPI.tasks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import me.drkmatr1984.GriefSaveAPI.utils.SBlock;

public class DelayedRegenTask implements Runnable
{
	protected final Block block;
	protected final Material mat;
	protected final byte bite;
	protected final BlockState blockState;
	protected final SBlock sb;
	
	public DelayedRegenTask(Block block, Material mat, byte bite, BlockState blockState, SBlock sb){
		this.block = block;
		this.mat = mat;
		this.bite = bite;
		this.blockState = blockState;
		this.sb = sb;
	}

	public void run() {
	}
}