package fan.frozen.inventoryuimanager;

import fan.frozen.inventoryuimanager.commands.UIManagementCommand;
import fan.frozen.inventoryuimanager.inventory.constructor.SinglePageInventory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * @author thework
 * @version alpha-1.0<br>
 *  a developing minecraft spigot Inventory UI API<br>
 */
public final class InventoryUIManager extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        for (int i = 0; i < 50; i++) {
            new SinglePageInventory(Bukkit.createInventory(null,54),"test"+i,this);
        }
        Objects.requireNonNull(getCommand("uimanager")).setExecutor(new UIManagementCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
