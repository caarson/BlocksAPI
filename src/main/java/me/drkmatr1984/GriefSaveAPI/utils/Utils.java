package me.drkmatr1984.GriefSaveAPI.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Utils{
	
	public static ArrayList<?> setToList(Set<?> set){
		ArrayList list = new ArrayList();
		if(!set.equals(null) && !set.isEmpty()){
			for(Object sb : set)
	    	{
	    		list.add(sb);
	    	}
		} 	
    	return list;
	}
	
	public static Set<?> listToSet(List<?> list){
		HashSet set = new HashSet();
		if(!list.equals(null) && !list.isEmpty())
		{
	    	for(Object sb : list)
	    	{
	    		set.add(sb);
	    	}
		}
    	return set;
	}
	
	public static boolean isOtherAttachable(Material mat){
		switch(mat){
			case SNOW: return true;
			case LONG_GRASS: return true;
			case YELLOW_FLOWER: return true;
			case RED_ROSE: return true;
			case CARPET: return true;
			case DEAD_BUSH: return true;
			case BROWN_MUSHROOM: return true;
			case RED_MUSHROOM: return true;
			default: break;		
		}
		return false;
	}
}