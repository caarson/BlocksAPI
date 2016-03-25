package me.drkmatr1984.GriefSaveAPI.utils;

import org.bukkit.Material;

public class StringUtils{
	public static boolean isDoor(Material mat){
		String matString = mat.name().toString();
		switch(matString){
			case "ACACIA_DOOR":
				return true;
			case "DARK_OAK_DOOR":
				return true;
			case "BIRCH_DOOR":
				return true;
			case "JUNGLE_DOOR":
				return true;
			case "IRON_DOOR":
				return true;
			case "SPRUCE_DOOR":
				return true;
			case "WOOD_DOOR":
				return true;
			case "WOODEN_DOOR":
				return true;
		}
		return false;
	}
}