package baguchan.revampedwolf.api;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;

public interface IHasInventory {
	boolean hasInventoryChanged(Container container);

	SimpleContainer getContainer();
}
