package fan.frozen.inventoryuimanager.inventory.instance.inventories;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import fan.frozen.inventoryuimanager.Utils.ItemUtil;
import fan.frozen.inventoryuimanager.inventory.compnents.Button;
import fan.frozen.inventoryuimanager.inventory.compnents.Label;
import fan.frozen.inventoryuimanager.inventory.constants.BorderPreset;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.MultiPageInventory;
import fan.frozen.inventoryuimanager.inventory.instance.components.PageFlipperDown;
import fan.frozen.inventoryuimanager.inventory.instance.components.PageFlipperUp;
import fan.frozen.inventoryuimanager.management.InformationCore;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class UIManagementInventory {
    MultiPageInventory multiPageInventory;

    public UIManagementInventory() {
    }
    public void initialize(){
        multiPageInventory = new MultiPageInventory(ChatColor.of(new Color(0x0080FF))+"ManagerUI",InventoryUIManager.getProvidingPlugin(InventoryUIManager.class),true,Bukkit.createInventory(null,54,"InventoryManagement"));
        Label label = new Label("page counter",new ItemStack(Material.PAPER),49,true,"page: "+multiPageInventory.getCurrentDisplayIndex(),multiPageInventory.getMaxPageCount()+" pages in total");
        multiPageInventory.registerComponent(label, multiPageInventory.ALL_PAGE_INDEXES);
        multiPageInventory.registerComponent(new PageFlipperUp("pagerFlipperUp",ItemUtil.getItemWithName(Material.ARROW, ChatColor.of(new Color(0x0080FF))+"previous"),48,true),multiPageInventory.ALL_PAGE_INDEXES);
        multiPageInventory.registerComponent(new PageFlipperDown("pagerFlipperDown",ItemUtil.getItemWithName(Material.ARROW, ChatColor.of(new Color(0xFF6200))+"next"),50,true), multiPageInventory.ALL_PAGE_INDEXES);
        multiPageInventory.registerComponent(
                BorderPreset.HORIZONTAL_BORDER.getBorder(
                        multiPageInventory.getAbsInventory()
                        ,false
                        ,ItemUtil.getItemWithName(Material.GRAY_STAINED_GLASS_PANE,ChatColor.of(new Color(0x949A94))+"border")
                        ,5
                ),
                multiPageInventory.ALL_PAGE_INDEXES
        );
        for (AbstractInventory abstractInventory : InformationCore.getInstance().getRegisteredInventory()) {
            if (multiPageInventory.getInventory().firstEmpty()>35){
                multiPageInventory.addPages(Bukkit.createInventory(null,54,"InventoryManagement"));
                multiPageInventory.felipPageDown();
            }
            ItemStack itemStack = new ItemStack(Material.CHEST);
            ItemUtil.changeItemName(itemStack,abstractInventory.getInventoryName());
            ItemUtil.changeItemLore(itemStack
                    ,"Type: "+abstractInventory.getUiType().getType()
                    ,"register: "+abstractInventory.getPlugin().getName()
                    ,"hash: "+abstractInventory.hashCode()
            );
            multiPageInventory.registerComponent(new Button(abstractInventory.getInventoryName(),itemStack,multiPageInventory.getInventory().firstEmpty()) {
                @Override
                public void activeOnTrigger(InventoryClickEvent event) {
                    SecondaryManagementInventory secondaryManagementInventory = new SecondaryManagementInventory(abstractInventory.getInventoryName()+" manager",InventoryUIManager.getProvidingPlugin(InventoryUIManager.class),abstractInventory,multiPageInventory);
                    multiPageInventory.setUnregisterComponents(false);
                    event.getWhoClicked().openInventory(secondaryManagementInventory.getInventory());
                }
            });
        }
        multiPageInventory.setCurrentPage(multiPageInventory.getPage(0));
        multiPageInventory.setCurrentPageIndex(0);
        label.setText("page: "+multiPageInventory.getCurrentDisplayIndex(),multiPageInventory.getMaxPageCount()+" pages in total");
    }
    public void switchInventory(Player player){
        initialize();
        multiPageInventory.openInventory(player);
    }
}
