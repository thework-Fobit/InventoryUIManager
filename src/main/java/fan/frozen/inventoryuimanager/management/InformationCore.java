package fan.frozen.inventoryuimanager.management;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * information core, which stores all the registered inventory object
 */
public class InformationCore {
    private static InformationCore informationCore;
    public ArrayList<AbstractInventory> registeredInventory = new ArrayList<>();
    public static InformationCore getInstance(){
        if (informationCore==null){
            informationCore = new InformationCore();
        }
        return informationCore;
    }
    public void removeInventory(AbstractInventory abstractInventory){
        getRegisteredInventory().remove(abstractInventory);
    }
    public void registerInventory(AbstractInventory abstractInventory){
        getRegisteredInventory().add(abstractInventory);
    }
    @SuppressWarnings("unchecked")
    public ArrayList<AbstractInventory> getRegisteredInventory(){
        ArrayList<AbstractInventory> abstractInventories = null;
        try {
            Class<?> aClass = Class.forName("fan.frozen.inventoryuimanager.management.InformationCore");
            Field registeredInventory = aClass.getField("registeredInventory");
            Object o = registeredInventory.get(this);
            abstractInventories = (ArrayList<AbstractInventory>)o;
        }catch (Exception e){
            e.printStackTrace();
        }
        return abstractInventories;
    }
}
