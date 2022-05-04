package fan.frozen.inventoryuimanager.inventory.compnents;

import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryClickEventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * a component which can trigger by player click
 */
public abstract class Button extends AbstractComponent<InventoryClickEvent> {
    public Button(String componentName,ItemStack material,int location){
        this(componentName,material,location,false);
    }
    public Button(String componentName,ItemStack material,int location,boolean consistence){
        super(material,location,consistence, ComponentType.BUTTON,componentName);
    }
    //all kind of component will automatically register the handler when it is registered just like this one
    @Override
    public void register(AbstractInventory abstractInventory) {
            this.bondedInventory = abstractInventory;
            abstractInventory.registerHandler(
                    new InventoryClickEventHandler(abstractInventory, abstractInventory.getPlugin()){
                        @Override
                        public void onActive(InventoryClickEvent event) {
                            event.setCancelled(true);
                            if (hashCodes.size()>0){
                                if (event.getSlot()==location&&hashCodes.contains(event.getInventory().hashCode())){
                                    activeOnTrigger(event);
                                }
                            }

                        }
                    }
            );
    }
}
