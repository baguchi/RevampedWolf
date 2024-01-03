package baguchan.revampedwolf;

import baguchan.revampedwolf.client.ClientRegistrar;
import baguchan.revampedwolf.network.ClientWolfScreenOpenPacket;
import baguchan.revampedwolf.network.WolfVariantPacket;
import baguchan.revampedwolf.registry.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RevampedWolf.MODID)
public class RevampedWolf {
    public static final String MODID = "revampedwolf";

    public RevampedWolf(IEventBus modEventBus) {
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::setupPackets);
        IEventBus bus = modEventBus;
        ModItems.ITEM_REGISTRY.register(bus);
        if (Dist.CLIENT == FMLEnvironment.dist) {
            modEventBus.addListener(ClientRegistrar::setup);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WolfConfigs.COMMON_SPEC);
    }

    public void setupPackets(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.play(WolfVariantPacket.ID, WolfVariantPacket::new, payload -> payload.client(WolfVariantPacket::handle));
        registrar.play(ClientWolfScreenOpenPacket.ID, ClientWolfScreenOpenPacket::new, payload -> payload.client(ClientWolfScreenOpenPacket::handle));
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code

    }
}
