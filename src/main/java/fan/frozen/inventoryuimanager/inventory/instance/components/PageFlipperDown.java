package fan.frozen.inventoryuimanager.inventory.instance.components;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.compnents.Button;
import fan.frozen.inventoryuimanager.inventory.compnents.Label;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.MultiPageInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * an example instance of how you can customize your component
 */
public class PageFlipperDown extends Button {
    private MultiPageInventory multiPageInventory;
    private Label pageCounter;

    public PageFlipperDown(String componentName, ItemStack material, int location, boolean consistence) {
        super(componentName, material, location, consistence);
    }
    @Override
    public void register(AbstractInventory abstractInventory) {
        super.register(abstractInventory);
        initialize();
    }
    void initialize(){
        multiPageInventory = (MultiPageInventory) getBondedInventory();
        for (Component<? extends Event> registeredComponent : multiPageInventory.getRegisteredComponents()) {
            if (registeredComponent instanceof Label label){
                if (label.getComponentName().equals("page counter")) {
                    pageCounter = label;
                }
            }
        }
    }
    @Override
    public void activeOnTrigger(InventoryClickEvent event) {
        if (multiPageInventory.getCurrentPageIndex()+1== multiPageInventory.getMaxPageCount()){
            return;
        }
        multiPageInventory.felipPageDown();
        pageCounter.setText("page: "+multiPageInventory.getCurrentDisplayIndex(),multiPageInventory.getMaxPageCount()+" pages in total");
        multiPageInventory.openInventory((Player) event.getWhoClicked());
    }
}
