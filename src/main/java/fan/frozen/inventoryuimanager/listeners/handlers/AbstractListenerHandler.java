package fan.frozen.inventoryuimanager.listeners.handlers;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import fan.frozen.inventoryuimanager.listeners.core.AbstractListener;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryEvent;

/**
 * handler is the object that deals with inventories and components so
 * if you want your inventory to have trigger logic,
 * you should use a handler instead of a listener
 * @param <T> used to define which kind of event this handler handling
 */
public abstract class AbstractListenerHandler <T extends Event>{
    AbstractInventory AbsInventory;
    AbstractListener<T> abstractListener;
    public AbstractListenerHandler(AbstractInventory abstractInventory){
        AbsInventory = abstractInventory;
    }

    /**
     * <p>this method will automatically call up by the listener</p>
     * By checking the hash code between the inventory clicked by the player and the inventory to which the handler bonded to
     * if they are the same, true then running logic will be triggered,
     * in case the running logic is called up every time a player clicks at a random inventory
     * @param event automatically delivered by the Listener
     */
    public final void inventoryEventHandle(T event){
        if (event==null){
            return;
        }
        InventoryEvent inventoryEvent = event instanceof InventoryEvent ? (InventoryEvent) event : null;
        if (inventoryEvent==null){
            return;
        }
        if (AbsInventory.getInventory().hashCode()==inventoryEvent.getInventory().hashCode()){
            onActive(event);
        }
    }

    /**
     * set the abstractInventory which bonded with the handler
     * @param absInventory the abstractInventory which bonded with the handler
     */
    public void setAbsInventory(AbstractInventory absInventory) {
        AbsInventory = absInventory;
    }

    /**
     * get the abstractInventory which bonded with the handler
     * @return the abstractInventory which bonded with the handler
     */
    public AbstractInventory getAbsInventory() {
        return AbsInventory;
    }

    /**
     * get listener which deal with this handler
     * @return the listener that deal with this handler
     */
    public AbstractListener<T> getAbstractListener() {
        return abstractListener;
    }

    /**
     * set listener which deal with this handler
     * @param abstractListener the listener that deal with this handler
     */
    public void setAbstractListener(AbstractListener<T> abstractListener) {
        this.abstractListener = abstractListener;
    }

    /**
     * this method will automatically be called up by {@link AbstractListenerHandler#inventoryEventHandle(Event)}
     * once the inventory pass by the hash code check
     * <p>override this method to customize your running logic</p>
     * @param event deliver by {@link AbstractListenerHandler#inventoryEventHandle(Event)}
     */
    public abstract void onActive(T event);

    /**
     * @see AbstractListenerHandler#getAbstractListener()
     * @return the listener that deal with this handler
     */
    public abstract AbstractListener<T> getListener();

    /**
     * should be defined to unregister the listener
     */
    public abstract void unregisterListener();

    /**
     * initialize method usually used to register listener for the handler
     * usually be called up at the constructor
     */
    abstract void initialize();

}
