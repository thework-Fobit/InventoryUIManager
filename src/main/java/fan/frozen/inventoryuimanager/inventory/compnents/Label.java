package fan.frozen.inventoryuimanager.inventory.compnents;

import fan.frozen.inventoryuimanager.Utils.ItemUtil;
import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryClickEventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * <p>a component which used to display text.</p>
 * if you want to use a component that can trigger by click, a Label is not recommended
 * @see Button
 */

public class Label extends AbstractComponent<InventoryClickEvent> {
    private String[] texts;

    /**
     * @param itemStack please see {@link AbstractComponent#AbstractComponent(ItemStack, int, boolean, ComponentType,String)}
     * @param location please see {@link AbstractComponent#AbstractComponent(ItemStack, int, boolean, ComponentType,String)}
     * @param texts the texts you want to display on the label, if the texts are more than one, the first input will become the name of the item
     *              while the others become the lore of the item
     */
    public Label(String componentName,ItemStack itemStack,int location,String... texts){
        this(componentName,itemStack,location,false,texts);
    }
    public Label(String componentName,ItemStack itemStack,int location,boolean consistence,String... texts){
        super(itemStack,location,consistence, ComponentType.LABEL,componentName);
        this.texts = texts;
        ItemUtil.changeItemName(itemStack,texts[0]);
        String[] template = new String[texts.length-1];
        System.arraycopy(texts,1,template,0,texts.length-1);
        ItemUtil.changeItemLore(itemStack,template);
    }


    @Override
    public void register(AbstractInventory abstractInventory) {
        this.bondedInventory = abstractInventory;
        abstractInventory.registerHandler(
                new InventoryClickEventHandler(abstractInventory, abstractInventory.getPlugin())
                {
                    @Override
                    public void onActive(InventoryClickEvent event) {
                        event.setCancelled(true);
                        activeOnTrigger(event);
                    }
                }
        );
    }


    @Override
    public void activeOnTrigger(InventoryClickEvent event) {

    }

    /**
     * you can change the display text use this method
     * @param texts texts you want display on the label
     */
    public void setText(String... texts) {
        this.texts = texts;
        ItemUtil.changeItemName(material,texts[0]);
        String[] template = new String[texts.length-1];
        System.arraycopy(texts,1,template,0,texts.length-1);
        ItemUtil.changeItemLore(material,template);
        bondedInventory.getInventory().setItem(location,material);
    }

    /**
     * get the texts which label are displaying right now
     * @return the texts which label are displaying right now
     */
    public String[] getText() {
        return texts;
    }
}
