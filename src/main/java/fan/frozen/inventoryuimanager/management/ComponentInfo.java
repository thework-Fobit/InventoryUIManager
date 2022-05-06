package fan.frozen.inventoryuimanager.management;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.api.Info;
import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import org.bukkit.plugin.Plugin;

public class ComponentInfo implements Info {
    private final String name;
    private final Plugin plugin;
    private final ComponentType componentType;
    private final int hashCode;
    public ComponentInfo(Component<?> component) {
        name = component.getComponentName();
        plugin = component.getBondedInventory().getPlugin();
        componentType = component.getComponentType();
        hashCode = component.hashCode();
    }

    @Override
    public String[] getInfo() {
        return new String[]{
                "name: "+name
                ,"register: "+plugin.getName()
                ,"componentType: "+componentType.getType()
                ,"hash: "+hashCode
        };
    }
}
