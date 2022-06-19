package fan.frozen.inventoryuimanager.inventory.compnents;

import fan.frozen.inventoryuimanager.Utils.ItemUtil;
import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.AnvilInventory;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryClickEventHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * A component can let you input text
 */
public class TextField extends AbstractComponent<InventoryClickEvent>{
    public TextField(String componentName,ItemStack material, int location, boolean consistency) {
        super(material, location, consistency, ComponentType.TEXT_FIELD, componentName);
    }

    @Override
    public void register(AbstractInventory abstractInventory) {
        this.bondedInventory = abstractInventory;
        abstractInventory.registerComponent(new TextFieldTriggerButton());
    }

    @Override
    public void activeOnTrigger(InventoryClickEvent event) {
        initialize((Player) event.getWhoClicked());
    }

    /**
     * @param text the text which user input
     * @param cost the cost of this operation
     */
    public void onComplete(String text,int cost){

    }

    private class TextFieldTriggerButton extends Button{

        public TextFieldTriggerButton() {
            super(TextField.this.componentName, TextField.this.material, TextField.this.location);
        }

        @Override
        public void activeOnTrigger(InventoryClickEvent event) {
            TextField.this.activeOnTrigger(event);
        }
    }

    /**
     * which initialize the UI of the text input
     * @param player the player who trying to use this component(automatically fill by method {@link TextField#activeOnTrigger(InventoryClickEvent)})
     */
    void initialize(Player player){
        AnvilInventory instance = AnvilInventory.getInstance(player, componentName, bondedInventory.getPlugin());
        instance.getInventory().setItem(0, ItemUtil.getItemWithName(Material.PAPER,"please enter content"));
        class AnvilEventHandler extends InventoryClickEventHandler{
            public AnvilEventHandler(AbstractInventory abstractInventory, Plugin plugin) {
                super(abstractInventory, plugin);
            }

            @Override
            public void onActive(InventoryClickEvent event) {
                if (event.getSlot()==2){
                    org.bukkit.inventory.AnvilInventory inventory = (org.bukkit.inventory.AnvilInventory) event.getInventory();
                    event.setCancelled(true);
                    onComplete(inventory.getRenameText(),inventory.getRepairCost());
                    event.getWhoClicked().closeInventory();
                }
            }
        }
        instance.registerHandler(new AnvilEventHandler(instance, instance.getPlugin()));
        instance.openInventory(player);
    }
}
