package fan.frozen.inventoryuimanager.inventory.constructor;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.constants.UIType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

/**
 * node inventory class<br>
 * you can use this class to construct a node inventory
 * it can have upper inventory and lower inventory
 * you can ues this object to easily get access to them
 * also you can choose this node to be a single page inventory or a multiple page inventory
 */
public class NodeInventory extends AbstractInventory{
    private AbstractInventory upperInventory;
    private AbstractInventory lowerInventory;
    private final AbstractInventory UI;
    public NodeInventory(AbstractInventory upperInventory,AbstractInventory lowerInventory,AbstractInventory UI) {
        super(UI.getInventory(), UI.getInventoryName(), UIType.NODE_INVENTORY,UI.shouldUnregisterComponents(),UI.getPlugin());
        this.upperInventory = upperInventory;
        this.lowerInventory = lowerInventory;
        this.UI = UI;
    }

    @Override
    public <T extends Event> void registerComponent(Component<T> component) {
        UI.registerComponent(component);
    }

    @Override
    public AbstractInventory getAbsInventory() {
        return UI;
    }

    @Override
    public Inventory getInventory() {
        return UI.getInventory();
    }

    public AbstractInventory getUpperInventory() {
        return upperInventory;
    }

    public void setUpperInventory(AbstractInventory upperInventory) {
        this.upperInventory = upperInventory;
    }

    public AbstractInventory getLowerInventory() {
        return lowerInventory;
    }

    public void setLowerInventory(AbstractInventory lowerInventory) {
        this.lowerInventory = lowerInventory;
    }
    public boolean hasLowerInventory(){
        return lowerInventory==null;
    }
    public boolean hasUpperInventory(){
        return upperInventory==null;
    }
    public AbstractInventory getUI() {
        return UI;
    }

    @Override
    public void openInventory(Player player) {
        UI.openInventory(player);
    }
}
