package fan.frozen.inventoryuimanager.api;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * interface which define how NMS work
 */
public interface NMSVersionWrapper {

    ServerPlayer getNMSPlayer(Player player);

    Inventory getBukkitInventory(AbstractContainerMenu menu);

    void setPlayerContainerMenu(Player player,AbstractContainerMenu menu);

    void initializePlayerMenu(Player player,AbstractContainerMenu menu);

    void openMenuForPlayer(Player player, int containerID, MenuType<?> menuType, String title);
}
