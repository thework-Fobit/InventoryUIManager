package fan.frozen.inventoryuimanager.inventory.XMLAnalyzer.handlers;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageHandler {
    private final Inventory inventory;
    private String contentPath;
    private String contentID;
    public PageHandler(String name,int size){
        inventory = Bukkit.createInventory(null,size,name);
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    @SuppressWarnings("unchecked")
    public void analyzePath(){
        File file = new File(InventoryUIManager.getProvidingPlugin(InventoryUIManager.class).getDataFolder()+"/"+contentPath);
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        List<Map<?, ?>> mapList = configuration.getMapList(contentID);
        HashMap<Integer, ItemStack> map = (HashMap<Integer, ItemStack>) mapList.get(0);
        for (Map.Entry<Integer, ItemStack> entry : map.entrySet()) {
            inventory.setItem(entry.getKey(),entry.getValue());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
