package fan.frozen.inventoryuimanager.listeners.handlers;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.core.AbstractListener;
import fan.frozen.inventoryuimanager.listeners.core.InventoryOpenEventListener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

/**
 * used to handle inventory open event
 */
public class InventoryOpenEventHandler extends AbstractListenerHandler<InventoryOpenEvent> {

    public InventoryOpenEventHandler(AbstractInventory abstractInventory, Plugin plugin) {
        super(abstractInventory, plugin);
        initialize();
    }

    @Override
    public void initialize() {
        abstractListener = InventoryOpenEventListener.getInstance(plugin);
        abstractListener.register(this);
    }

    @Override
    public AbstractListener<InventoryOpenEvent> getListener() {
        return abstractListener;
    }

    @Override
    public void unregisterListener() {
        getListener().unregister(this);
    }

    @Override
    public void onActive(InventoryOpenEvent event) {

    }
}
