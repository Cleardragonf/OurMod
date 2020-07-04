package com.cleardragonf.ourmod;

import com.cleardragonf.ourmod.MCM.MCMValueCapability;
import com.cleardragonf.ourmod.MCM.MCMValueProvider;
import com.cleardragonf.ourmod.entity.EntityEffects;
import com.cleardragonf.ourmod.events.SurvivalEvents;
import com.cleardragonf.ourmod.network.NetRegistries;
import com.cleardragonf.ourmod.objects.blocks.*;
import com.cleardragonf.ourmod.world.OreGeneration;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cleardragonf.ourmod.init.BlockInitNew;
import com.cleardragonf.ourmod.init.ItemInitNew;
import com.cleardragonf.ourmod.init.ModContainerTypes;
import com.cleardragonf.ourmod.init.ModTileEntityTypes;
import com.cleardragonf.ourmod.setup.ClientProxy;
import com.cleardragonf.ourmod.setup.IProxy;
import com.cleardragonf.ourmod.setup.ModSetup;
import com.cleardragonf.ourmod.setup.ServerProxy;

import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ourmod")
@Mod.EventBusSubscriber(modid = OurMod.MOD_ID, bus = Bus.MOD)
public class OurMod
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static Optional<Boolean> Debug = Optional.of(false);
    
    //Communication of Server vs Client
    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(location("main"), () -> "1", "1"::equals, "1"::equals);
    public static ModSetup setup = new ModSetup();

    public static ResourceLocation location (String name){
        return new ResourceLocation("ourmod", name);
    }

    public final static String MOD_ID = "ourmod";
    public static OurMod instance;


    public OurMod() {
    	
    	final IEventBus modEventBus= FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::doClientStuff);

        ItemInitNew.ITEMS.register(modEventBus);
        BlockInitNew.BLOCKS.register(modEventBus);
        
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        instance = this;
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event) {
        EntityEffects.registerAll(event.getRegistry());
    }
    
    @SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		
		BlockInitNew.BLOCKS.getEntries().stream().filter(block -> !(block.get() instanceof TomatoCrop))
                .filter(block -> !(block.get() instanceof RiceCrop))
                .filter(block -> !(block.get() instanceof OnionCrop))
                .filter(block -> !(block.get() instanceof CornCrop))
                .filter(block -> !(block.get() instanceof CucumberCrop))
                .filter(block -> !(block.get() instanceof BroccoliCrop))
                .filter(block -> !(block.get() instanceof LettuceCrop))
                .filter(block -> !(block.get() instanceof PeanutCrop))
                .filter(block -> !(block.get() instanceof PepperCrop))
                .filter(block -> !(block.get() instanceof CauliflowerCrop))
                .filter(block -> !(block.get() instanceof EggplantCrop))
                .map(RegistryObject::get).forEach(block -> {
			final Item.Properties properties = new Item.Properties().group(OurModItemGroup.instance);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});



		LOGGER.debug("Registered BlockItems!");
	}
    public final static MCMValueCapability mcmValue = new MCMValueCapability();
    public static Map<ItemStack, Integer> mcmList = new HashMap<>();

    private void setup(final FMLCommonSetupEvent event)
    {
    	setup.init();
    	proxy.init();
    	LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        SurvivalEvents.registerHeatMap();
        SurvivalEvents.registerBiomeHeatMap();
        MCMValueProvider.registerCapability();
        NetRegistries.registerMSG();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }


    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event){
        OreGeneration.setupOreGeneration();
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    public static class OurModItemGroup extends ItemGroup{
    	public static final OurModItemGroup instance = new OurModItemGroup(ItemGroup.GROUPS.length, "ourmodtab");
    	
    	private OurModItemGroup(int index, String label) {
    		super(index, label);
    	}
    	
    	@Override
    	public ItemStack createIcon() {
    		return new ItemStack(BlockInitNew.FORCEFIELD.get());
    	}
    }

    private void testing(){

    }
}
