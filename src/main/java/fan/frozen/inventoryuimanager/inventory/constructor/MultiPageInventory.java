package fan.frozen.inventoryuimanager.inventory.constructor;

import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.compnents.Border;
import fan.frozen.inventoryuimanager.inventory.constants.UIType;
import fan.frozen.inventoryuimanager.listeners.handlers.AbstractListenerHandler;
import fan.frozen.inventoryuimanager.listeners.handlers.InventoryCloseEventHandler;
import fan.frozen.inventoryuimanager.management.InformationCore;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

/**
 * multiple page inventory class<br>
 * you can use this class to construct a multiple page inventory
 */
public class MultiPageInventory extends AbstractInventory{
    private int currentPageIndex;
    private Inventory currentPage;
    private Inventory[] allPages;
    public int[] ALL_PAGE_INDEXES;

    /**
     * @param allPages inventories you need to display
     *                  each inventory present a page
     */
    public MultiPageInventory(String inventoryName,Plugin plugin,Inventory... allPages) {
        this(inventoryName,plugin,true,allPages);
    }
    public MultiPageInventory(String inventoryName,Plugin plugin,boolean unregisterComponents,Inventory... allPages) {
        super(inventoryName,UIType.MULTI_PAGE_INVENTORY,unregisterComponents,plugin);
        currentPage = allPages[0];
        setAllPages(allPages);
        initialize();
    }

    /**
     * change current page to previous page<br>
     * if is first page then do nothing
     */
    public void felipPageUp(){
        if (getCurrentPageIndex()>0){
            setCurrentPageIndex(getCurrentPageIndex()-1);
        }
        currentPage = getPage(currentPageIndex);
    }

    /**
     * change current page to next page<br>
     * if is final page then do nothing
     */
    public void felipPageDown(){
        if (getCurrentPageIndex()<getMaxPageCount()-1){
            setCurrentPageIndex(getCurrentPageIndex()+1);
        }
        currentPage = getPage(currentPageIndex);
    }

    /**
     * get how many pages in this inventory
     * @return how many pages in this inventory
     */
    public int getMaxPageCount() {
        return allPages.length;
    }

    /**
     * get current page
     * @return current page
     */
    public Inventory getCurrentPage() {
        return currentPage;
    }

    /**
     * set current page
     * @param currentPage page you want to set as the current page
     */
    public void setCurrentPage(Inventory currentPage) {
        if(containPage(currentPage)){
            setCurrentPageIndex(getPageIndex(currentPage));
        }else {
            addPages(currentPage);
            setCurrentPageIndex(allPages.length-1);
        }
        this.currentPage = currentPage;
    }

    /**
     * get all the pages in the inventory
     * @return all the pages in the inventory
     */
    public Inventory[] getAllPages() {
        return allPages;
    }

    /**
     * set all the pages in the inventory
     * @param allPages set all the pages in the inventory
     */
    public void setAllPages(Inventory[] allPages) {
        this.allPages = allPages;
        getAllPageIndex();
    }

    /**
     * get current page index in the array
     * @return current page index in the array
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    /**
     * current page index in the array plus one
     * @return current page index in the array plus one
     */
    public int getCurrentDisplayIndex(){
        return getCurrentPageIndex()+1;
    }

    /**
     * set current page index in the array
     * @param currentPageIndex current page index in the array
     */
    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    /**
     * add new pages to the inventory<br>
     * add multiple page in one time is supported
     * @param newPages the pages you want to add to the inventory
     */
    public void addPages(Inventory... newPages){
        Inventory[] array = new Inventory[getMaxPageCount()+newPages.length];
        System.arraycopy(this.allPages,0,array,0,getMaxPageCount());
        System.arraycopy(newPages,0,array,getMaxPageCount(),newPages.length);
        for (Inventory newPage : newPages) {
            for (Component<? extends Event> registeredComponent : registeredComponents) {
                if (registeredComponent.isConsist()){
                    registeredComponent.addCanWorkAt(newPage);
                    if (registeredComponent instanceof Border border){
                        border.deployBorder();
                    }else {
                        newPage.setItem(registeredComponent.getLocation(),registeredComponent.getMaterial());
                    }
                }
            }
        }
        setAllPages(array);
    }

    /**
     * remove appointed page from the inventory
     * @param index the index of the page you want remove in the array
     */
    public void removePage(int index){
        if (index<getMaxPageCount()&&index>0){
            Inventory[] newPages = new Inventory[getMaxPageCount()-1];
            System.arraycopy(this.allPages,0,newPages,0,index-1);
            System.arraycopy(this.allPages,index+1,newPages,index,getMaxPageCount()-index);
            setAllPages(newPages);
        }
    }
    @Override
    public Inventory getInventory() {
        return currentPage;
    }

    /**
     * get page by index
     * @param index page index in the array
     * @return inventory page
     */
    public Inventory getPage(int index){
        if (index<getMaxPageCount()&&index>-1){
            return allPages[index];
        }
        return currentPage;
    }
    public final void initialize(){
        new InventoryCloseEventHandler(this, plugin) {
            @Override
            public void onActive(InventoryCloseEvent closeEvent) {
               if (unregisterComponents){
                   onClose(closeEvent);
                   for (AbstractListenerHandler<? extends Event> handler : handlers) {
                       handler.unregisterListener();
                   }
                   this.unregisterListener();
               }
            }
        };
    }

    /**
     * register one component to multiple pages at one time
     * @param component the component you want register
     * @param index the pages you want to register
     * @param <T> the trigger type of the component
     */
    public <T extends Event> void registerComponent(Component<T> component,int... index){
        component.setOnlyWorkAt(getPages(index));
        component.register(getAbsInventory());
        for (int i : index) {
            if (!(component instanceof Border)){
                getPage(i).setItem(component.getLocation(),component.getMaterial());
            }
        }
        registeredComponents.add(component);
    }

    @Override
    public <T extends Event> void registerComponent(Component<T> component) {
        registerComponent(component, getCurrentPageIndex());
    }

    @Override
    public AbstractInventory getAbsInventory() {
        return this;
    }

    /**
     * automatically called up by {@link MultiPageInventory#initialize()}
     * which initialize static field {@link MultiPageInventory#ALL_PAGE_INDEXES}
     */
    private void getAllPageIndex(){
        this.ALL_PAGE_INDEXES = new int[allPages.length];
        for (int i = 0; i < allPages.length ; i++) {
            ALL_PAGE_INDEXES[i] = i;
        }
    }

    /**
     * get multiple pages in one time by index
     * @param index pages indexes you want to get
     * @return pages
     */
    private Inventory[] getPages(int... index){
        ArrayList<Inventory> inventories = new ArrayList<>();
        for (int i : index) {
            inventories.add(getPage(i));
        }
        return inventories.toArray(Inventory[]::new);
    }

    public void setUnregisterComponents(boolean unregisterComponents) {
        this.unregisterComponents = unregisterComponents;
    }

    /**
     * call up when inventory is closed<br>
     * you can override it to customize your own running logic
     * @param closeEvent spigot original event object
     */
    public void onClose(InventoryCloseEvent closeEvent){

    }

    /**
     * open current page for the player
     * @param player player you need to open the UI
     */
    @Override
    public void openInventory(Player player) {
        player.closeInventory();
        player.openInventory(getCurrentPage());
    }

    boolean containPage(Inventory page){
        for (Inventory allPage : allPages) {
            if (allPage.equals(page)){
                return true;
            }
        }
        return false;
    }

    int getPageIndex(Inventory page){
        for (int i = 0; i < allPages.length; i++) {
            if (allPages[i].equals(page)){
                return i;
            }
        }
        return -1;
    }
}
