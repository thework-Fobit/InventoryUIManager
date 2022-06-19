package fan.frozen.inventoryuimanager.inventory.instance.inventories;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import fan.frozen.inventoryuimanager.Utils.ItemUtil;
import fan.frozen.inventoryuimanager.inventory.compnents.Button;
import fan.frozen.inventoryuimanager.inventory.compnents.Label;
import fan.frozen.inventoryuimanager.inventory.compnents.TextField;
import fan.frozen.inventoryuimanager.inventory.constants.BorderPreset;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.MultiPageInventory;
import fan.frozen.inventoryuimanager.inventory.instance.components.PageFlipperDown;
import fan.frozen.inventoryuimanager.inventory.instance.components.PageFlipperUp;
import fan.frozen.inventoryuimanager.management.InformationCore;
import fan.frozen.inventoryuimanager.management.InventoryInfo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class UIManagementInventory {
    MultiPageInventory innerInventory;

    public UIManagementInventory() {

    }
    public void initialize(){
        innerInventory = new MultiPageInventory(ChatColor.of(new Color(0x0080FF))+"ManagerUI",InventoryUIManager.getProvidingPlugin(InventoryUIManager.class),true,Bukkit.createInventory(null,54,"InventoryManagement")){
            @Override
            public void onClose(InventoryCloseEvent closeEvent) {
                InformationCore.getInstance().removeInventory(this);
            }
        };
        Label label = new Label("page counter",new ItemStack(Material.PAPER),49,true,"page: "+ innerInventory.getCurrentDisplayIndex(), innerInventory.getMaxPageCount()+" pages in total");
        innerInventory.registerComponent(label, innerInventory.ALL_PAGE_INDEXES);
        innerInventory.registerComponent(new PageFlipperUp("pagerFlipperUp",ItemUtil.getItemWithName(Material.ARROW, ChatColor.of(new Color(0x0080FF))+"previous"),48,true), innerInventory.ALL_PAGE_INDEXES);
        innerInventory.registerComponent(new PageFlipperDown("pagerFlipperDown",ItemUtil.getItemWithName(Material.ARROW, ChatColor.of(new Color(0xFF6200))+"next"),50,true), innerInventory.ALL_PAGE_INDEXES);
        innerInventory.registerComponent(
                BorderPreset.HORIZONTAL_BORDER.getBorder(
                        innerInventory.getAbsInventory()
                        ,false
                        ,ItemUtil.getItemWithName(Material.GRAY_STAINED_GLASS_PANE,ChatColor.of(new Color(0x949A94))+"border")
                        ,5
                ),
                innerInventory.ALL_PAGE_INDEXES
        );
        getAllRegisteredInventory();
        innerInventory.setCurrentPage(innerInventory.getPage(0));
        innerInventory.setCurrentPageIndex(0);
        label.setText("page: "+ innerInventory.getCurrentDisplayIndex(), innerInventory.getMaxPageCount()+" pages in total");
    }
    public void switchInventory(Player player){
        initialize();
        innerInventory.openInventory(player);
    }
    void getAllRegisteredInventory(){
        for (AbstractInventory abstractInventory : InformationCore.getInstance().getRegisteredInventory()) {
            if (innerInventory.getInventory().firstEmpty()>35){
                innerInventory.addPages(Bukkit.createInventory(null,54,"InventoryManagement"));
                innerInventory.felipPageDown();
            }
            ItemStack itemStack = new ItemStack(Material.CHEST);
            ItemUtil.changeItemName(itemStack,abstractInventory.getInventoryName());
            ItemUtil.changeItemLore(itemStack,new InventoryInfo(abstractInventory).getInfo());
            innerInventory.registerComponent(new Button(abstractInventory.getInventoryName(),itemStack, innerInventory.getInventory().firstEmpty()) {
                @Override
                public void activeOnTrigger(InventoryClickEvent event) {
                    SecondaryManagementInventory secondaryManagementInventory = new SecondaryManagementInventory(abstractInventory.getInventoryName()+" manager",InventoryUIManager.getProvidingPlugin(InventoryUIManager.class),abstractInventory, innerInventory);
                    innerInventory.setUnregisterComponents(false);
                    event.getWhoClicked().openInventory(secondaryManagementInventory.getInventory());
                }
            });
        }
    }
}
