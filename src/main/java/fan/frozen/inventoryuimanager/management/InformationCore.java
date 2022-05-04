package fan.frozen.inventoryuimanager.management;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;

import java.util.ArrayList;

/**
 * information core, which stores all the registered inventory object
 */
public class InformationCore {
    public static ArrayList<AbstractInventory> registeredInventory = new ArrayList<>();
}
