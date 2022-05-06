package fan.frozen.inventoryuimanager.management;

import fan.frozen.inventoryuimanager.api.Info;
import fan.frozen.inventoryuimanager.inventory.constants.UIType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import org.bukkit.plugin.Plugin;

public class InventoryInfo implements Info {
    private final String name;
    private final Plugin plugin;
    private final UIType uiType;
    private final int hashCode;
    public InventoryInfo(AbstractInventory abstractInventory){
        name = abstractInventory.getInventoryName();
        plugin = abstractInventory.getPlugin();
        uiType = abstractInventory.getUiType();
        hashCode = abstractInventory.hashCode();
    }

    public String[] getInfo(){
        return new String[]{
          "name: "+name
          ,"register: "+plugin.getName()
          ,"type: "+uiType.getType()
          ,"hash: "+hashCode
        };
    }
}
