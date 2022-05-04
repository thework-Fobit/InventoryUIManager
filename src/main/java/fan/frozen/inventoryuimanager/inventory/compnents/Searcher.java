package fan.frozen.inventoryuimanager.inventory.compnents;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import fan.frozen.inventoryuimanager.Utils.ItemUtil;
import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.AnvilInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.MultiPageInventory;
import fan.frozen.inventoryuimanager.inventory.constructor.NodeInventory;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryClickEventHandler;
import fan.frozen.inventoryuimanager.management.InformationCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Developing component
 */
public class Searcher extends AbstractComponent<InventoryClickEvent>{
    public Searcher(String componentName,ItemStack material, int location, boolean consistence) {
        super(material, location, consistence,ComponentType.SEARCHER,componentName);
    }
    //developing component, finish at next version
    @Override
    public void register(AbstractInventory abstractInventory) {
        this.bondedInventory = abstractInventory;
        NodeInventory nodeInventory = new NodeInventory(
                abstractInventory
                ,null
                ,new MultiPageInventory("search result",abstractInventory.getPlugin(),Bukkit.createInventory(null,54))
        );
        new Button("searcher",getMaterial(), getLocation(), isConsist()) {
            @Override
            public void activeOnTrigger(InventoryClickEvent event) {
                AnvilInventory searcher = AnvilInventory.getInstance((Player) event.getWhoClicked(), "searcher", InventoryUIManager.getProvidingPlugin(InventoryUIManager.class));
                searcher.openInventory((Player) event.getWhoClicked());
                searcher.getInventory().setItem(0, ItemUtil.getItemWithName(Material.PAPER,"enter words to search"));
                searcher.registerHandler(new InventoryClickEventHandler(abstractInventory,abstractInventory.getPlugin()){
                    @Override
                    public void onActive(InventoryClickEvent event) {
                        String itemName = searcher.getAnvilInstance().itemName;
                        ArrayList<ItemStack> allItems = new ArrayList<>();
                        if (!Objects.equals(itemName, "enter words to search")) {
                            for (AbstractInventory abstractInventory : InformationCore.registeredInventory) {
                                if (abstractInventory.getInventoryName().contains(itemName)) {
                                    allItems.add(ItemUtil.getItemWithName(Material.BOOK,abstractInventory.getInventoryName()));
                                }
                                for (Component<? extends Event> registeredComponent : abstractInventory.getRegisteredComponents()) {
                                    ItemMeta itemMeta = registeredComponent.getMaterial().getItemMeta();
                                    assert itemMeta!=null;
                                    if (itemMeta.getLore()==null) {
                                        continue;
                                    }
                                    if (itemMeta.getDisplayName().contains(itemName)||itemMeta.getLore().contains(itemName)){
                                        allItems.add(ItemUtil.getItemWithName(Material.BOOK, abstractInventory.getInventoryName()));
                                    }
                                }
                            }
                            registerItemToInventory((MultiPageInventory) nodeInventory.getUI(),allItems.toArray(ItemStack[]::new));
                        }
                    }
                });
            }
        };
    }
    public void registerItemToInventory(MultiPageInventory multiPageInventory,ItemStack... itemStacks){
        for (ItemStack itemStack : itemStacks) {
            if (multiPageInventory.getInventory().firstEmpty() > 35) {
                multiPageInventory.addPages(Bukkit.createInventory(null, 54));
                multiPageInventory.felipPageDown();
            }
            multiPageInventory.getInventory().addItem(itemStack);
        }
    }
    @Override
    public void activeOnTrigger(InventoryClickEvent event) {

    }
}
