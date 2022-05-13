package fan.frozen.inventoryuimanager.listeners.handlers;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.core.AbstractListener;
import fan.frozen.inventoryuimanager.listeners.core.InventoryClickEventListener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

/**
 * handler used to handle inventory click event
 */
public class InventoryClickEventHandler extends AbstractListenerHandler<InventoryClickEvent> {

    public InventoryClickEventHandler(AbstractInventory abstractInventory, Plugin plugin) {
        super(abstractInventory,plugin);
    }
    @Override
    public void initialize(){
        abstractListener = InventoryClickEventListener.getInstance(plugin);
        abstractListener.register(this);
    }

    @Override
    public AbstractListener<InventoryClickEvent> getListener() {
        return abstractListener;
    }

    @Override
    public void unregisterListener() {
        getListener().unregister(this);
    }

    @Override
    public void onActive(InventoryClickEvent event) {

    }

}
