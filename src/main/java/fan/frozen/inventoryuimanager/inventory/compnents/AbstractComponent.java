package fan.frozen.inventoryuimanager.inventory.compnents;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * component interface realization
 * @param <T> component trigger type
 */
public abstract class AbstractComponent <T extends Event> implements Component<T> {
    protected ItemStack material;
    protected int location;
    protected final ArrayList<Integer> hashCodes = new ArrayList<>();
    protected boolean consistency;
    protected final ComponentType componentType;
    protected AbstractInventory bondedInventory;
    protected final String componentName;
    /**
     * @param material the item which presents the component in the inventory
     * @param location the location of the component in the inventory
     * @param consistency means when you have a multiple page inventory, you have registered the component at one of the pages, then should this component be extended to other pages
     * @param componentType appoint what kind of this component is
     */
    public AbstractComponent(ItemStack material, int location, boolean consistency, ComponentType componentType,String componentName){
        this.material = material;
        this.location = location;
        this.consistency = consistency;
        this.componentType = componentType;
        this.componentName = componentName;
    }


    @Override
    public ItemStack getMaterial() {
        return material;
    }

    @Override
    public int getLocation() {
        return location;
    }

    public void setLocation(int location){
        this.location = location;
    }

    public void setMaterial(ItemStack material){
        this.material = material;
    }
    @Override
    public void setOnlyWorkAt(Inventory... inventories) {
        hashCodes.clear();
        for (Inventory inventory : inventories) {
            hashCodes.add(inventory.hashCode());
        }
    }

    @Override
    public void addCanWorkAt(Inventory... addInventories) {
        for (Inventory addInventory : addInventories) {
            hashCodes.add(addInventory.hashCode());
        }
    }

    @Override
    public ComponentType getComponentType() {
        return componentType;
    }

    @Override
    public boolean isConsist() {
        return consistency;
    }

    @Override
    public void setConsist(boolean consistency) {
        this.consistency = consistency;
    }

    @Override
    public void unregister() {
        bondedInventory.getRegisteredComponents().remove(this);
        bondedInventory.getInventory().setItem(this.getLocation(),null);
    }

    public void reloadComponent(){
        if (bondedInventory!=null){
            unregister();
            bondedInventory.registerComponent(this);
        }
    }

    /**
     *  get the abstractInventory which the component belongs to
     * @return abstractInventory the component belongs to
     */
    public AbstractInventory getBondedInventory() {
        return bondedInventory;
    }

    /**
     * hash code is used to check if the component should work on an abstractInventory object
     * so this method is basically used to get all the appointed inventories hash codes
     * @return all the appointed inventories hash codes
     */
    public ArrayList<Integer> getHashCodes() {
        return hashCodes;
    }

    public String getComponentName() {
        return componentName;
    }
}
