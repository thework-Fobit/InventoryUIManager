package fan.frozen.inventoryuimanager.inventory.constructor;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.api.NMSVersionWrappers.CurrentVersionWrapper;
import fan.frozen.inventoryuimanager.inventory.constants.UIType;
import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryCloseEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

/**
 * anvil inventory used NMS to realize<br>
 * you can create an anvil inventory by using this class
 */
public class AnvilInventory extends AbstractInventory{
    private final AnvilInstance instance;

    /**
     * please use {@link AnvilInventory#getInstance(Player, String, Plugin)} to get object
     * @param instance automatically fill by {@link AnvilInventory#getInstance(Player, String, Plugin)}
     * @param inventoryName automatically fill by {@link AnvilInventory#getInstance(Player, String, Plugin)}
     * @param uiType automatically fill by {@link AnvilInventory#getInstance(Player, String, Plugin)}
     * @param unregisterComponents automatically fill by {@link AnvilInventory#getInstance(Player, String, Plugin)}
     * @param plugin automatically fill by {@link AnvilInventory#getInstance(Player, String, Plugin)}
     */
    private AnvilInventory(AnvilInstance instance, String inventoryName, UIType uiType, boolean unregisterComponents, Plugin plugin) {
        super(CurrentVersionWrapper.getInstance().getBukkitInventory(instance), inventoryName, uiType, unregisterComponents, plugin);
        this.instance = instance;
        initialize();
    }

    /**
     * initialize GC system(automatically call up, please don't manually call up)
     */
    public final void initialize(){
        new InventoryCloseEventHandler(this,plugin){
            @Override
            public void onActive(InventoryCloseEvent event) {
                instance.setItem(0,0,ItemStack.EMPTY);
                if (unregisterComponents){
                    for (AbstractListenerHandler<? extends Event> handler : handlers) {
                        handler.unregisterListener();
                    }
                    this.unregisterListener();
                }
            }
        };
    }

    /**
     * this inventory now don't support register component
     */
    @Override
    public final <T extends Event> void registerComponent(Component<T> component) {

    }

    /**
     * @return abstract inventory
     */
    @Override
    public AbstractInventory getAbsInventory() {
        return this;
    }

    /**
     * @param player player who you need to open the inventory
     * @param title the title you need menu to display
     * @param plugin plugin who register the inventory
     * @return AnvilInventory instance
     */
    public static AnvilInventory getInstance(Player player,String title,Plugin plugin){
        CurrentVersionWrapper instance = CurrentVersionWrapper.getInstance();
        return new AnvilInventory(
              new AnvilInstance(instance.getNMSPlayer(player),title)
             ,title
             ,UIType.ANVIL_INVENTORY
             ,true
             , plugin
     );
    }

    /**
     * open inventory for spigot player object
     * @param player player you need to open the UI
     */
    public void openInventory(Player player){
        CurrentVersionWrapper wrapper = CurrentVersionWrapper.getInstance();
        wrapper.openMenuForPlayer(player,instance.getContainerId(),MenuType.ANVIL,getInventoryName());
        wrapper.setPlayerContainerMenu(player,instance);
        wrapper.initializePlayerMenu(player,instance);
    }

    /**
     * set operation will cost how many levels
     * @param cost the level the operation will cost
     */
    public void setOperationCost(int cost){
        instance.cost.set(cost);
    }

    /**
     * @return anvil instance
     */
    public AnvilInstance getAnvilInstance() {
        return instance;
    }

    /**
     * customize NMS Anvil menu by extend NMS Anvil menu
     */
    public static class AnvilInstance extends AnvilMenu{
        public AnvilInstance(ServerPlayer player, String title) {
            super(player.nextContainerCounter(),player.getInventory(), ContainerLevelAccess.create(player.level,new BlockPos(0,0,0)));
            this.checkReachable = false;
            this.setTitle(new TextComponent(title));
        }

        @Override
        protected void onTake(net.minecraft.world.entity.player.Player entityhuman, ItemStack itemstack) {

        }

        @Override
        public void createResult() {
            super.createResult();
        }
        public int getContainerId(){
            return this.containerId;
        }
    }
}
