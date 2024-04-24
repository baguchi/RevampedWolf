package baguchan.revampedwolf;

import baguchan.revampedwolf.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RevampedWolf.MODID)
public class RevampedWolf {
    public static final String MODID = "revampedwolf";

    public RevampedWolf(IEventBus modEventBus) {
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        ModItems.ITEM_REGISTRY.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WolfConfigs.COMMON_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code

    }
}
