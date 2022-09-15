package fan.frozen.inventoryuimanager.inventory.XMLAnalyzer.handlers;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;

public class InventoryHandler {
    private final String name;
    private Constructor<?> constructor;
    private final Plugin register;
    private Component<?>[] components;
    private Inventory[] pages;
    public InventoryHandler(String name,String type,Plugin register){
        this.name = name;
        this.register = register;
        try {
            Class<?> clazz = Class.forName(type);
            constructor = clazz.getDeclaredConstructor(String.class, Plugin.class, Inventory[].class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public AbstractInventory initializeInventory() throws Exception{
        Object o = constructor.newInstance(name, register, pages);
        AbstractInventory abstractInventory = (AbstractInventory) o;
        for (Component<?> component : components) {
            abstractInventory.registerComponent(component);
        }
        return abstractInventory;
    }

    public void setComponents(Component<?>[] components) {
        this.components = components;
    }

    public void setPages(Inventory[] pages) {
        this.pages = pages;
    }
}
