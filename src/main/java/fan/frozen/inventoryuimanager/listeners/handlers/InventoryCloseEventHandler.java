package fan.frozen.inventoryuimanager.listeners.handlers;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.core.AbstractListener;
import fan.frozen.inventoryuimanager.listeners.core.InventoryCloseEventListener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

/**
 * handler used to handle inventory close event
 */
public class InventoryCloseEventHandler extends AbstractListenerHandler<InventoryCloseEvent> {

    public InventoryCloseEventHandler(AbstractInventory abstractInventory, Plugin plugin) {
        super(abstractInventory,plugin);
    }

    @Override
    public void initialize() {
        abstractListener = InventoryCloseEventListener.getInstance(plugin);
        abstractListener.register(this);
    }

    @Override
    public AbstractListener<InventoryCloseEvent> getListener() {
        return abstractListener;
    }

    @Override
    public void unregisterListener() {
        getListener().unregister(this);
    }

    @Override
    public void onActive(InventoryCloseEvent event) {

    }
}
