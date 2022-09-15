package fan.frozen.inventoryuimanager.inventory.XMLAnalyzer.handlers;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import fan.frozen.inventoryuimanager.api.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Objects;

public class ComponentHandler {
    private final String name;
    private Constructor<?> constructor;
    private final int location;
    private ItemStack material;
    private final boolean consistency;
    public ComponentHandler(String name,String type,int location,boolean consistency){
        this.name = name;
        this.location = location;
        this.consistency = consistency;
        try {
            Class<?> clazz = Class.forName(type);
            constructor = clazz.getDeclaredConstructor(String.class, ItemStack.class,int.class,boolean.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Component<?> getComponent(){
        Object o = null;
        try {
            o = constructor.newInstance(name, material, location, consistency);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (Component<?>) o;
    }
    public void setMaterial(String path,String materialID){
        File file = new File(InventoryUIManager.getProvidingPlugin(InventoryUIManager.class).getDataFolder()+"/"+path);
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        Object o = configuration.get(materialID);
        ItemStack itemStack = o instanceof ItemStack ? ((ItemStack) o) : null;
        material = Objects.requireNonNullElseGet(itemStack, () -> new ItemStack(Material.BARRIER));
    }
}
