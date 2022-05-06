package fan.frozen.inventoryuimanager.inventory.compnents;


import fan.frozen.inventoryuimanager.Utils.CommonUtil;
import fan.frozen.inventoryuimanager.inventory.constants.ComponentType;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryClickEventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * the border component you can deploy
 */
public class Border extends AbstractComponent<InventoryClickEvent> {
    private int[] deploySlots;
    private final ArrayList<Inventory> canWorkAt = new ArrayList<>();

    public Border(String componentName,ItemStack material, int location, boolean consistence) {
        this(componentName,material,location,consistence,new int[0]);
    }
    public Border(String componentName,ItemStack material,int location,boolean consistence,int[] deploySlots){
        super(material, location, consistence, ComponentType.BORDER,componentName);
        this.deploySlots = deploySlots;
    }

    @Override
    public void register(AbstractInventory abstractInventory) {
        this.bondedInventory = abstractInventory;
        deployBorder();
        abstractInventory.registerHandler(
                new InventoryClickEventHandler(abstractInventory, abstractInventory.getPlugin()){
                    @Override
                    public void onActive(InventoryClickEvent event) {
                        int hashCode;
                        if (event.getClickedInventory()==null) {
                            hashCode = -1;
                        }else {
                            hashCode = event.getClickedInventory().hashCode();
                        }
                        if (hashCodes.size()>0){
                            if (hashCodes.contains(event.getInventory().hashCode())&&CommonUtil.intArrayContains(deploySlots,event.getSlot())&&hashCode==event.getView().getTopInventory().hashCode()){
                                event.setCancelled(true);
                                activeOnTrigger(event);
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void activeOnTrigger(InventoryClickEvent event) {

    }

    /*
     * when you create a new border object of the constructor
     * the location you input will only store as an int array in this object
     * when you use this method, then it will read these locations and put
     * the material in to the locations you have appointed
     */

    /**
     * used to deploy the border
     */
    public void deployBorder(){
        if (deploySlots!=null){
            if (deploySlots.length>0) {
                for (Inventory itemStacks : canWorkAt) {
                    for (int deploySlot : deploySlots) {
                        itemStacks.setItem(deploySlot,material);
                    }
                }
            }
        }
    }

    /**
     * set the locations you want to deploy the border
     * @param deploySlots the locations you want to deploy the border
     */
    public void setDeploySlots(int[] deploySlots) {
        this.deploySlots = deploySlots;
    }

    /**
     * add the locations you want to deploy the border
     * @param slots add the locations to the array
     */
    public void addSlots(int... slots){
        this.deploySlots = CommonUtil.addIntegerElements(deploySlots,slots);
    }

    /**
     * get the locations where the border will be deployed
     * @return locations where the border will be deployed
     */
    public int[] getDeploySlots() {
        return deploySlots;
    }

    @Override
    public void setOnlyWorkAt(Inventory... inventories) {
        canWorkAt.clear();
        Collections.addAll(canWorkAt,inventories);
        super.setOnlyWorkAt(inventories);
    }

    @Override
    public void addCanWorkAt(Inventory... addInventories) {
        Collections.addAll(canWorkAt,addInventories);
        super.addCanWorkAt(addInventories);
    }
}
