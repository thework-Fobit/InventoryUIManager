package fan.frozen.inventoryuimanager.inventory.instance.inventories;

import fan.frozen.inventoryuimanager.Utils.CommonUtil;
import fan.frozen.inventoryuimanager.Utils.ItemUtil;
import fan.frozen.inventoryuimanager.Utils.TextUtil;
import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.compnents.Border;
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
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.util.ArrayList;

public class SecondaryManagementInventory extends MultiPageInventory {
    private final AbstractInventory monitorInventory;
    private final AbstractInventory upperInventory;
    public SecondaryManagementInventory(String inventoryName, Plugin plugin, AbstractInventory monitorInventory,AbstractInventory upperInventory) {
        super (inventoryName, plugin,Bukkit.createInventory(null,54,inventoryName));
        this.monitorInventory = monitorInventory;
        this.upperInventory = upperInventory;
        initializeComponents();
    }
    public void initializeComponents(){
        ArrayList<Component<? extends Event>> registeredComponents = monitorInventory.getRegisteredComponents();
        Label label = new Label("page counter",new ItemStack(Material.PURPLE_STAINED_GLASS_PANE),49,true,"page: "+getCurrentDisplayIndex(),getMaxPageCount()+" pages in total");
        this.registerComponent(label,ALL_PAGE_INDEXES);
        this.registerComponent(new Button("backToUpperInventory",ItemUtil.getItemWithName(Material.ARROW,"back to upper inventory"),0,true) {
            @Override
            public void activeOnTrigger(InventoryClickEvent event) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(upperInventory.getInventory());
            }
        },ALL_PAGE_INDEXES);
        this.registerComponent(new PageFlipperUp("pagerFlipperUp",ItemUtil.getItemWithName(Material.LIGHT_BLUE_STAINED_GLASS_PANE,"previous"),45,true),ALL_PAGE_INDEXES);
        this.registerComponent(new PageFlipperDown("pagerFlipperDown",ItemUtil.getItemWithName(Material.LIME_STAINED_GLASS_PANE,"next"),53,true),ALL_PAGE_INDEXES);
        Border border = BorderPreset.AROUND_BORDER.getBorder(this, false, ItemUtil.getItemWithName(Material.GRAY_STAINED_GLASS_PANE, ChatColor.of(new Color(0x949A94)) + "border"), 54);
        this.registerComponent(border,ALL_PAGE_INDEXES);
        for (Component<? extends Event> registeredComponent : registeredComponents) {
            if (this.getInventory().firstEmpty()==-1){
                this.addPages(Bukkit.createInventory(null,54,inventoryName));
                this.felipPageDown();
            }
            this.registerComponent(new Label(registeredComponent.getComponentName(),new ItemStack(Material.WRITABLE_BOOK),this.getInventory().firstEmpty()
                    ,registeredComponent.getComponentType().getType()
                    ,TextUtil.getTag("componentLocation: ") +ChatColor.of(new Color(0xAB19D9))+registeredComponent.getLocation()
                    ,TextUtil.getTag("componentMaterial: ")+ChatColor.of(new Color(0xFFC000))+registeredComponent.getMaterial().getType().name()
                    ,TextUtil.getTag("consistency: ")+CommonUtil.getColor(registeredComponent.isConsist())
            ){
                @Override
                public void activeOnTrigger(InventoryClickEvent event) {
                    if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)&&event.getSlot()==location){
                        unregister();
                    }
                }
            });
        }
        setCurrentPage(getPage(0));
        setCurrentPageIndex(0);
        label.setText("page: "+getCurrentDisplayIndex(),getMaxPageCount()+" pages in total");
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        InformationCore.registeredInventory.remove(this);
    }
}
