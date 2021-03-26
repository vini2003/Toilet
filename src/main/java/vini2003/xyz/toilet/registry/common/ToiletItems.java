package vini2003.xyz.toilet.registry.common;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import vini2003.xyz.toilet.Toilet;
import vini2003.xyz.toilet.common.item.PlungerItem;

public class ToiletItems {
	public static final Item TOILET = Registry.register(
			Registry.ITEM,
			Toilet.identifier("toilet"),
			new BlockItem(
					ToiletBlocks.TOILET,
					new FabricItemSettings()
							.group(ToiletItemGroups.TOILET)
							.maxCount(1)
			)
	);
	
	public static final Item PLUNGER = Registry.register(
			Registry.ITEM,
			Toilet.identifier("plunger"),
			new PlungerItem(
					new FabricItemSettings()
						.maxCount(1)
						.group(ToiletItemGroups.TOILET)
			)
	);
	
	public static void initialize() {
	
	}
}
