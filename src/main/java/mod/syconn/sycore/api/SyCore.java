package mod.syconn.sycore.api;

import mod.syconn.sycore.api.network.Network;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD)
public class SyCore
{
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD);

    public SyCore()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupClient);
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupCapabilities);
    }

    private void setupCommon(FMLCommonSetupEvent event)
    {
        Network.init();
    }

    private void setupClient(FMLClientSetupEvent event)
    {
        //ClientHandler.instance().setup();
    }

    public void setupCapabilities(RegisterCapabilitiesEvent event)
    {

    }
}
