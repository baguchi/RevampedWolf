package baguchan.revampedwolf;

import baguchan.revampedwolf.registry.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RevampedWolf.MODID)
public class RevampedWolf {
    public static final String MODID = "revampedwolf";

    public RevampedWolf(ModContainer modContainer, Dist dist, IEventBus modEventBus) {
        // Register the setup method for modloading
        if (dist.isClient()) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
        modEventBus.addListener(this::setup);
        ModItems.ITEM_REGISTRY.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, WolfConfig.COMMON_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code

    }
}
