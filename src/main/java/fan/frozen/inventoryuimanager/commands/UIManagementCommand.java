package fan.frozen.inventoryuimanager.commands;

import fan.frozen.inventoryuimanager.inventory.instance.inventories.UIManagementInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UIManagementCommand implements CommandExecutor {
    public static final UIManagementInventory inventory = new UIManagementInventory();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player){
            inventory.switchInventory(player);
        }
        return false;
    }
}
