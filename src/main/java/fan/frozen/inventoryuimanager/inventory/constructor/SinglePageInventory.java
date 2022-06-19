package fan.frozen.inventoryuimanager.inventory.constructor;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.constants.UIType;
import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryCloseEventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

/**
 * single page inventory class<br>
 * different from original spigot inventory is that you can bond
 * this inventory with a handler, so you don't have to create another
 * listener to listen to this inventory's activity, you can just override
 * the {@code onActive(T event)} in the handler to customize your running logic
 */
public class SinglePageInventory extends AbstractInventory {
    public SinglePageInventory(String inventoryName,Plugin plugin,Inventory... inventory) {
        this(inventoryName,true,plugin,inventory);
    }
    //parameter unregister components means choice when you close the inventory the components inside this inventory should be unregistered or not
    public SinglePageInventory(String inventoryName,boolean unregisterComponents,Plugin plugin,Inventory... inventory) {
        super(inventory[0],inventoryName, UIType.SINGLE_PAGE_INVENTORY,unregisterComponents,plugin);
        initialize();
        this.unregisterComponents = unregisterComponents;
    }

    public final void initialize() {
        new InventoryCloseEventHandler(this, plugin) {
            @Override
            public void onActive(InventoryCloseEvent closeEvent) {
                onClose(closeEvent);
                if (unregisterComponents){
                    for (AbstractListenerHandler<? extends Event> handler : handlers) {
                        handler.unregisterListener();
                    }
                    this.unregisterListener();
                }
            }
        };
    }

    @Override
    public <T extends Event> void registerComponent(Component<T> component) {
        component.setOnlyWorkAt(getInventory());
        component.register(getAbsInventory());
        getInventory().setItem(component.getLocation(),component.getMaterial());
        registeredComponents.add(component);
    }

    @Override
    public AbstractInventory getAbsInventory() {
        return this;
    }

    public void onClose(InventoryCloseEvent event){

    }

    public void setUnregisterComponents(boolean unregisterComponents) {
        this.unregisterComponents = unregisterComponents;
    }

    @Override
    public void openInventory(Player player) {
        player.closeInventory();
        player.openInventory(getInventory());
    }
}