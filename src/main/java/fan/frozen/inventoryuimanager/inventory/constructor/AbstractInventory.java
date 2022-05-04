package fan.frozen.inventoryuimanager.inventory.constructor;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.constants.UIType;
import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import fan.frozen.inventoryuimanager.management.InformationCore;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * customize abstractInventory class
 */
public abstract class AbstractInventory {
    protected Inventory inventory;
    protected ArrayList<AbstractListenerHandler<? extends Event>> handlers = new ArrayList<>();
    protected ArrayList<Component<? extends Event>> registeredComponents = new ArrayList<>();
    protected final String inventoryName;
    protected final UIType uiType;
    protected boolean unregisterComponents;
    protected final Plugin plugin;

    /**
     * @param inventoryName unique name for the abstractInventory
     * @param uiType present the type of the abstractInventory
     * @param unregisterComponents should the components which bonded to this abstractInventory be unregistered after the inventory closed
     *                             (if you are using static object to store inventory object, turn this to false)
     * @param plugin used to classify which plugin registered this UI
     */
    public AbstractInventory(String inventoryName,UIType uiType,boolean unregisterComponents,Plugin plugin) {
        this.inventoryName = inventoryName;
        this.uiType = uiType;
        this.plugin = plugin;
        this.unregisterComponents = unregisterComponents;
        InformationCore.registeredInventory.add(this);
    }

    /**
     * @param inventory original spigot inventory object
     */
    public AbstractInventory(Inventory inventory,String inventoryName,UIType uiType,boolean unregisterComponents,Plugin plugin){
        this.inventoryName =inventoryName;
        this.uiType = uiType;
        this.inventory = inventory;
        this.unregisterComponents = unregisterComponents;
        this.plugin = plugin;
        InformationCore.registeredInventory.add(this);
    }
    public Inventory getInventory(){
        return inventory;
    }

    /**
     * used to register handler for abstract inventory
     * @param handler the handler you need to register
     */
    public void registerHandler(AbstractListenerHandler<? extends Event> handler){
        handlers.add(handler);
    }

    /**
     * register handler by reflection
     * @param clazz the handler you need to register's class
     * @param <T> the type of the handler
     */
    public <T extends AbstractListenerHandler<? extends Event>> void registerHandler(Class<T> clazz){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(AbstractInventory.class, Plugin.class);
            Object o = declaredConstructor.newInstance(this,providingPlugin);
            AbstractListenerHandler<? extends Event> handler = (AbstractListenerHandler<? extends Event>) o;
            handlers.add(handler);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * get all registered components
     * @return all registered components
     */
    public ArrayList<Component<? extends Event>> getRegisteredComponents() {
        return registeredComponents;
    }

    /**
     * @return abstract inventory name
     */
    public String getInventoryName() {
        return inventoryName;
    }

    /**
     * @return the type of the UI there are few preset of the UIType, you can also customize your own UIType by extending
     * {@link UIType}
     * @see UIType
     */
    public UIType getUiType() {
        return uiType;
    }

    /**
     * @return which plugin registered this UI
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * @return should all components be unregistered after the UI was closed
     */
    public boolean shouldUnregisterComponents() {
        return unregisterComponents;
    }


    /*
       like multiple page inventory, it's openInventory method is to open current page of the inventory
       you can also change it to any page you want it to open for the player
     */
    /**
     * abstract method, the subclass should realize
     * @param player player you need to open the UI
     */
    public abstract void openInventory(Player player);

    /**
     * used to register components
     * @param component components need to be registered
     * @param <T> type of components handler represents
     */
    public abstract <T extends Event> void registerComponent(Component<T> component);

    /**
     * get AbstractInventory object
     * @return usually itself, but you can also customize it by override it
     */
    public abstract AbstractInventory getAbsInventory();
}
