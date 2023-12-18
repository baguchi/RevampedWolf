package baguchan.revampedwolf;

import baguchan.revampedwolf.client.ClientRegistrar;
import baguchan.revampedwolf.network.ClientWolfScreenOpenPacket;
import baguchan.revampedwolf.network.WolfVariantPacket;
import baguchan.revampedwolf.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RevampedWolf.MODID)
public class RevampedWolf {
    // Directly reference a log4j logger.
    public static final String MODID = "revampedwolf";
    private static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final String NETWORK_PROTOCOL = "2";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public RevampedWolf(IEventBus modEventBus) {
        this.setupMessage();
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        IEventBus bus = modEventBus;
        ModItems.ITEM_REGISTRY.register(bus);
        if (Dist.CLIENT == FMLEnvironment.dist) {
            modEventBus.addListener(ClientRegistrar::setup);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WolfConfigs.COMMON_SPEC);
    }


    private void setupMessage() {
        CHANNEL.messageBuilder(ClientWolfScreenOpenPacket.class, 0)
                .encoder(ClientWolfScreenOpenPacket::write).decoder(ClientWolfScreenOpenPacket::read)
                .consumerMainThread(ClientWolfScreenOpenPacket::handle)
                .add();
        CHANNEL.messageBuilder(WolfVariantPacket.class, 1)
                .encoder(WolfVariantPacket::write).decoder(WolfVariantPacket::read)
                .consumerMainThread(WolfVariantPacket::handle)
                .add();
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code

    }
}
