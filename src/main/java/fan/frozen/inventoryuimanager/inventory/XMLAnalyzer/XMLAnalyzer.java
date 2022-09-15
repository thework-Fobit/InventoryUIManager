package fan.frozen.inventoryuimanager.inventory.XMLAnalyzer;

import fan.frozen.inventoryuimanager.InventoryUIManager;
import fan.frozen.inventoryuimanager.api.Component;
import fan.frozen.inventoryuimanager.inventory.XMLAnalyzer.handlers.ComponentHandler;
import fan.frozen.inventoryuimanager.inventory.XMLAnalyzer.handlers.InventoryHandler;
import fan.frozen.inventoryuimanager.inventory.XMLAnalyzer.handlers.PageHandler;
import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;
import org.bukkit.inventory.Inventory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLAnalyzer extends DefaultHandler {
    private InventoryHandler currentInventoryHandler;
    private PageHandler currentPageHandler;
    private ComponentHandler currentComponentHandler;
    private final ArrayList<ComponentHandler> componentHandlers = new ArrayList<>();
    private final ArrayList<PageHandler> pageHandlers = new ArrayList<>();
    private final ArrayList<AbstractInventory> registeredInventories = new ArrayList<>();

    public XMLAnalyzer() {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        switch (qName){
            case "Inventory" -> currentInventoryHandler = new InventoryHandler(attributes.getValue("name"),attributes.getValue("type"), InventoryUIManager.getProvidingPlugin(InventoryUIManager.class));
            case "page" -> currentPageHandler = new PageHandler(attributes.getValue("name"),Integer.parseInt(attributes.getValue("size")));
            case "component" -> currentComponentHandler = new ComponentHandler(attributes.getValue("name"),attributes.getValue("type"),Integer.parseInt(attributes.getValue("location")),Boolean.getBoolean(attributes.getValue("consistency")));
            case "material" -> currentComponentHandler.setMaterial(attributes.getValue("path"),attributes.getValue("id"));
            case "contents"-> {
                currentPageHandler.setContentPath(attributes.getValue("path"));
                currentPageHandler.setContentID(attributes.getValue("id"));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        switch (qName){
            case "Inventory" -> {
                try {
                    registeredInventories.add(currentInventoryHandler.initializeInventory());
                }catch (Exception e){
                    e.printStackTrace();
                }
                currentInventoryHandler = null;
            }
            case "page" ->{
                currentPageHandler.analyzePath();
                pageHandlers.add(currentPageHandler);
                currentPageHandler = null;
            }
            case "pages" ->{
                ArrayList<Inventory> pages = new ArrayList<>();
                for (PageHandler pageHandler : pageHandlers) {
                    pages.add(pageHandler.getInventory());
                }
                currentInventoryHandler.setPages(pages.toArray(Inventory[]::new));
                pageHandlers.clear();
            }
            case "component" ->{
                componentHandlers.add(currentComponentHandler);
                currentComponentHandler = null;
            }
            case "components"->{
                ArrayList<Component<?>> components = new ArrayList<>();
                for (ComponentHandler componentHandler : componentHandlers) {
                    components.add(componentHandler.getComponent());
                }
                currentInventoryHandler.setComponents(components.toArray(Component<?>[]::new));
                componentHandlers.clear();
            }
        }
    }

    public ArrayList<AbstractInventory> getRegisteredInventories() {
        return registeredInventories;
    }
}
