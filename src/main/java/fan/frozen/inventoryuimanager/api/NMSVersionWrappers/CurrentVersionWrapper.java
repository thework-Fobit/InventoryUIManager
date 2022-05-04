package fan.frozen.inventoryuimanager.api.NMSVersionWrappers;

import fan.frozen.inventoryuimanager.api.NMSVersionWrapper;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * <p>provided NMS util class</p>
 * <p>further update can be change realization of this class to complete</p>
 * use {@link CurrentVersionWrapper#getInstance()} to get its instance
 */
public class CurrentVersionWrapper implements NMSVersionWrapper {
    private static CurrentVersionWrapper wrapper;
    private CurrentVersionWrapper(){

    }

    /**
     * turn spigot player object into original NMS player object
     * @param player spigot player object
     * @return original NMS player object
     */
    @Override
    public ServerPlayer getNMSPlayer(Player player) {
        return ((CraftPlayer)player).getHandle();
    }

    /**
     * turn original NMS ContainerMenu into spigot inventory object
     * @param menu original NMS ContainerMenu
     * @return spigot inventory object
     */
    @Override
    public Inventory getBukkitInventory(AbstractContainerMenu menu) {
        return menu.getBukkitView().getTopInventory();
    }

    /**
     * open NMS ContainerMenu for a spigot player object
     * @param player spigot player
     * @param menu NMS ContainerMenu
     */
    @Override
    public void setPlayerContainerMenu(Player player,AbstractContainerMenu menu) {
        getNMSPlayer(player).containerMenu = menu;
    }

    /**
     * initialize NMS ContainerMenu for spigot player, so the listener provided by spigot can listen to
     * NMS ContainerMenu
     * @param player spigot player
     * @param menu NMS ContainerMenu
     */
    @Override
    public void initializePlayerMenu(Player player,AbstractContainerMenu menu) {
        getNMSPlayer(player).initMenu(menu);
    }

    /**
     * use packet to open an inventory for spigot player
     * @param player spigot player
     * @param containerID containerID you can customize by yourself
     * @param menuType the menu type you need to open
     * @param title the title of the menu
     */
    @Override
    public void openMenuForPlayer(Player player,int containerID, MenuType<?> menuType,String title) {
        getNMSPlayer(player).connection.send(new ClientboundOpenScreenPacket(containerID,menuType,new TextComponent(title)));
    }

    /**
     * @return {@link CurrentVersionWrapper} instance
     */
    public static CurrentVersionWrapper getInstance(){
        if (wrapper==null){
            wrapper = new CurrentVersionWrapper();
        }
        return wrapper;
    }

}
