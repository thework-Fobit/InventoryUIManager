package fan.frozen.inventoryuimanager.inventory.compnents;

import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryClickEventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * a component which can trigger by player click
 */
public class Button extends AbstractComponent<InventoryClickEvent> {
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
            abstractInventory.registerHandler(new ButtonClickHandler(abstractInventory,abstractInventory.getPlugin()));
    }

    @Override
    public void activeOnTrigger(InventoryClickEvent event) {

    }

    private class ButtonClickHandler extends InventoryClickEventHandler{

        public ButtonClickHandler(AbstractInventory abstractInventory, Plugin plugin) {
            super(abstractInventory, plugin);
        }

        @Override
        public void onActive(InventoryClickEvent event) {
             /*
              event.getSlot() is for checking the player click on right position
              hashCodes.contains is for checking the player click on right inventory
              event.getView is for checking player is click on the menu instead of their inventory
            */
            int hashCode;
            if (event.getClickedInventory()==null) {
                hashCode = -1;
            }else {
                hashCode = event.getClickedInventory().hashCode();
            }
            if (hashCodes.size()>0){
                if (event.getSlot()==location
                    &&hashCodes.contains(event.getInventory().hashCode())
                    &&hashCode==event.getView().getTopInventory().hashCode()
                   )
                {
                    event.setCancelled(true);
                    activeOnTrigger(event);
                }
            }
        }
    }
}
