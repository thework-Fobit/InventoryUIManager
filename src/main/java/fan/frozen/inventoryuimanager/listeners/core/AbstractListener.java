package fan.frozen.inventoryuimanager.listeners.core;

import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

/**
 * abstract listener class is used to define what an inventory UI manager listener should do,
 * the listener should automatically call up the handler,
 * so if you want to handle an event you should create a handler class while creating a listener
 * <p>you can create your own listener by extend this class</p>
 * @param <T> define what kind of listener it is
 */
public abstract class AbstractListener <T extends Event> implements Listener {
    ArrayList<AbstractListenerHandler<T>> registerInventories = new ArrayList<>();
    boolean writeLock;
    ArrayList<AbstractListenerHandler<T>> templateAddingInventoryStores = new ArrayList<>();
    ArrayList<AbstractListenerHandler<T>> templateRemovingInventoryStores = new ArrayList<>();

    /**
     * define every listener should have a getEvent() method<br>
     * original spigot listener method, which used to call up handler
     * @param event the event which listener are listening
     */
    public abstract void getEvent(T event);

    /**
     *
     * @param plugin used to register event listener
     */
    public AbstractListener(Plugin plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    /**
     * used to get all the inventories which the listener is listening
     * @return all the inventories which the listener are listening
     */
    public ArrayList<AbstractListenerHandler<T>> getRegisterInventories() {
        return registerInventories;
    }

    /**
     * @see AbstractListener#lockThread()
     */
    public void unlockThread(){
        writeLock = false;
        if (this.templateAddingInventoryStores.size()>0){
            this.registerInventories.addAll(templateAddingInventoryStores);
        }
        if (this.templateRemovingInventoryStores.size()>0){
            this.registerInventories.removeAll(templateRemovingInventoryStores);
        }
        this.templateRemovingInventoryStores.clear();
        this.templateAddingInventoryStores.clear();
    }

    /**
     * this method is designed for thread synchronization and security
     *  if there is a component in which running logic is to register or unregister
     *  a component while he is running through every component in the inventory,
     *  it will cause ArrayList to throw {@link java.util.ConcurrentModificationException}
     *  so before the listener runs through handlers, we will call up this method so when it's running through the handlers
     *  it will put the elements to another ArrayList and after the process of running through is done,
     *  {@link AbstractListener#unlockThread()} should be called up then put the elements back into the original ArrayList
     *  and also clear up the template ArrayList
     *  <p>if you meet {@link java.util.ConcurrentModificationException} when you code, just try to add this method before you run through
     *      components elements in the ArrayList you defined<p/>
     */
    public void lockThread(){
        writeLock = true;
    }

    /**
     * this is an abstract method that defines that every inventory UI manager listener
     * should have a handler register logic
     * @param handler handler you need to register
     */
    public abstract void register(AbstractListenerHandler<T> handler);

    /**
     * this is an abstract method that defines that every inventory UI manager listener
     * should have a handler unregister logic
     * @param handler handler you need to unregister
     */
    public abstract void unregister(AbstractListenerHandler<T> handler);
}
