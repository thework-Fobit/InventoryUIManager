package fan.frozen.inventoryuimanager.listeners.core;

import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class InventoryClickEventListener extends AbstractListener<InventoryClickEvent> {
    private static InventoryClickEventListener clickEventListener;

    private InventoryClickEventListener(Plugin plugin) {
        super(plugin);
    }

    /**
     * original spigot listener method, which used to call up handler
     * @param inventoryClickEvent original spigot event object
     */
    @EventHandler
    public void getEvent(InventoryClickEvent inventoryClickEvent){
        lockThread();
        for (AbstractListenerHandler<InventoryClickEvent> registerInventory : this.registerInventories) {
            registerInventory.inventoryEventHandle(inventoryClickEvent);
        }
        unlockThread();
    }

    @Override
    public void register(AbstractListenerHandler<InventoryClickEvent> handler) {
        if (writeLock){
            this.templateAddingInventoryStores.add(handler);
        }else {
            this.registerInventories.add(handler);
        }
    }

    @Override
    public void unregister(AbstractListenerHandler<InventoryClickEvent> handler) {
        if (writeLock){
            this.templateRemovingInventoryStores.add(handler);
        }else {
            this.registerInventories.remove(handler);
        }
    }

    /**
     * use {@code getInstance()} method to get listener object,
     * instead of using the constructor to construct the object is very necessary,
     * all inventory UI manager class should have a static method called {@code getInstance()}
     * which returns its own static object, doing this just in case to prevent the same function listener
     * be initialized multiple times otherwise will cause a waste of the server's resource
     * @param plugin get the register's plugin
     * @return listener instance object
     */
    public static InventoryClickEventListener getInstance(Plugin plugin){
        if (clickEventListener==null){
            clickEventListener = new InventoryClickEventListener(plugin);
        }
        return clickEventListener;
    }
}
