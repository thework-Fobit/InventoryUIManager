package fan.frozen.inventoryuimanager.api;

import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.MultiPageInventory;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


/**
 * which define how a component work
 * @param <T> it can be all kind event, like if you want your component trigger by play click at it, you can use InventoryClickEvent
 *
 */
public interface Component <T extends Event> {
    /**
     * this method will automatically be called by inventory, and if you want to register a component
     * @see AbstractInventory#registerComponent(Component)
     * @param abstractInventory abstractInventory the component belongs to
     */
    void register(AbstractInventory abstractInventory);

    /**
     * this method will automatically be called by inventory when an inventory is closed
     * if you don't want the component to be unregistered when the inventory is closed,
     * please set {@link AbstractInventory}'s unregisteredComponents field to false
     * when you are constructing an abstractInventory object
     * you can also call it up manually when you want to unregister the component
     */
    void unregister();

    /**
     * override it to customize your components running logic
     * @param event event appointed by Generics
     */
    void activeOnTrigger(T event);

    /**
     * get what kind of item will your component be shown in the inventory
     * @return the looks of the component in inventory
     */
    ItemStack getMaterial();

    /**
     * get where your component will be shown in the inventory
     * @return the location of the component in the inventory
     */
    int getLocation();

    /**
     * when you have multiple page inventory, you can use this to appoint which inventory you want it to work on
     * if you want them work on all pages, you can use this parameter we provided
     * @param inventories the inventory objects you want components to work on
     * @see MultiPageInventory#ALL_PAGE_INDEXES
     */
    void setOnlyWorkAt(Inventory... inventories);

    /**
     * you can add specific page to your component workable pages array
     * @param addInventories the inventory objects you want add
     */
    void addCanWorkAt(Inventory... addInventories);

    /**
     * use this check the consistency of the component
     * @return is the component consistent
     */
    boolean isConsist();

    /**
     * @param consistency consistency you want to set
     *
     */
    void setConsist(boolean consistency);

    /**
     * @return the type of the component
     */
    ComponentType getComponentType();

    /**
     * @return components name
     */
    String getComponentName();
}
