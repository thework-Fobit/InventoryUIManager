package fan.frozen.inventoryuimanager.listeners.core;

import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

public class InventoryCloseEventListener extends AbstractListener<InventoryCloseEvent>{
    private static InventoryCloseEventListener inventoryCloseEventListener;

    private InventoryCloseEventListener(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void getEvent(InventoryCloseEvent inventoryCloseEvent){
        lockThread();
        for (AbstractListenerHandler<InventoryCloseEvent> registerInventory : registerInventories) {
            registerInventory.inventoryEventHandle(inventoryCloseEvent);
        }
        unlockThread();
    }

    @Override
    public void register(AbstractListenerHandler<InventoryCloseEvent> handler) {
        if (writeLock){
            this.templateAddingInventoryStores.add(handler);
        }else {
            this.registerInventories.add(handler);
        }
    }

    @Override
    public void unregister(AbstractListenerHandler<InventoryCloseEvent> handler) {
        if (writeLock){
            this.templateRemovingInventoryStores.add(handler);
        }else {
            this.registerInventories.remove(handler);
        }
    }

    /**
     * @see InventoryClickEventListener#getInstance(Plugin)
     */
    public static InventoryCloseEventListener getInstance(Plugin plugin){
        if (inventoryCloseEventListener==null){
            inventoryCloseEventListener = new InventoryCloseEventListener(plugin);
        }
        return inventoryCloseEventListener;
    }
}
