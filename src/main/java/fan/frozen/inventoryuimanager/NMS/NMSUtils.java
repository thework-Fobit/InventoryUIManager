package fan.frozen.inventoryuimanager.NMS;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;


/**
 * <p>test class</p>
 * if you want to use NMS related
 * @see fan.frozen.inventoryuimanager.api.NMSVersionWrappers.CurrentVersionWrapper
 */

public class NMSUtils {
    public static ServerPlayer getNMSPlayer(Player player){
        return ((CraftPlayer) player).getHandle();
    }

    public static void getAnvilByNMS(Player player,String anvilName,int id){
        ServerPlayer nmsPlayer = getNMSPlayer(player);
        Bukkit.getPluginManager().registerEvents(new NMSUtils.TestListener(), InventoryUIManager.getProvidingPlugin(InventoryUIManager.class));
        MenuType<AnvilMenu> anvil = MenuType.ANVIL;
        testAnvil testAnvil = new testAnvil(nmsPlayer, "test");
        CraftInventoryView bukkitView = testAnvil.getBukkitView();
        Inventory topInventory = bukkitView.getTopInventory();
        nmsPlayer.connection.send(new ClientboundOpenScreenPacket(testAnvil.getContainerId(),anvil ,new TextComponent(anvilName)));
        nmsPlayer.containerMenu = testAnvil;
        nmsPlayer.initMenu(testAnvil);
    }
    public static class TestListener implements Listener{
        @EventHandler
        public void listen(InventoryClickEvent event){
            System.out.println(event.getInventory());
        }
    }
}
class testAnvil extends AnvilMenu{
    public testAnvil(ServerPlayer player,String title) {
        super(player.nextContainerCounter(),player.getInventory(),ContainerLevelAccess.create(player.level,new BlockPos(0,0,0)));
        this.checkReachable = false;
        this.setTitle(new TextComponent(title));
    }

    @Override
    protected void onTake(net.minecraft.world.entity.player.Player entityhuman, ItemStack itemstack) {

    }

    @Override
    public void createResult() {
        super.createResult();
        this.cost.set(0);
    }
    public int getContainerId(){
        return this.containerId;
    }
}
