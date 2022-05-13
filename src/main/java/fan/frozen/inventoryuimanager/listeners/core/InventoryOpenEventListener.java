package fan.frozen.inventoryuimanager.listeners.core;

import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

public class InventoryOpenEventListener extends AbstractListener<InventoryOpenEvent> {
    private static InventoryOpenEventListener inventoryOpenEventListener;
    public InventoryOpenEventListener(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void getEvent(InventoryOpenEvent inventoryOpenEvent){
        lockThread();
        for (AbstractListenerHandler<InventoryOpenEvent> registerInventory : getRegisterInventories()) {
            registerInventory.inventoryEventHandle(inventoryOpenEvent);
        }
        unlockThread();
    }

    @Override
    public void register(AbstractListenerHandler<InventoryOpenEvent> handler) {
        if (writeLock){
            this.templateAddingInventoryStores.add(handler);
        }else {
            this.registerInventories.add(handler);
        }
    }

    @Override
    public void unregister(AbstractListenerHandler<InventoryOpenEvent> handler) {
        if (writeLock){
            this.templateRemovingInventoryStores.add(handler);
        }else {
            this.registerInventories.remove(handler);
        }
    }

    /**
     * @see InventoryClickEventListener#getInstance(Plugin)
     */
    public static InventoryOpenEventListener getInstance(Plugin plugin){
        if (inventoryOpenEventListener==null){
            inventoryOpenEventListener = new InventoryOpenEventListener(plugin);
        }
        return inventoryOpenEventListener;
    }
}
