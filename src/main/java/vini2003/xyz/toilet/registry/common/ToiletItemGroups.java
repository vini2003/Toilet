package vini2003.xyz.toilet.registry.common;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import vini2003.xyz.toilet.Toilet;

public class ToiletItemGroups {
	public static final ItemGroup TOILET = FabricItemGroupBuilder.create(Toilet.identifier("toilet")).icon(() -> new ItemStack(ToiletItems.TOILET)).build();
	
	public static void initialize() {
	
	}
}
