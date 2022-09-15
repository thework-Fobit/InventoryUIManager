package fan.frozen.inventoryuimanager.inventory.XMLAnalyzer;

import fan.frozen.inventoryuimanager.inventory.constructor.AbstractInventory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class XMLFileHandler {
    private ArrayList<AbstractInventory> registeredInventories = new ArrayList<>();
    public void initializeXMLFile(String path) throws Exception{
        initializeXMLFile(new File(path));
    }

    public void initializeXMLFile(File path) throws Exception{
        if (!path.exists()){
            throw new FileNotFoundException();
        }
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLAnalyzer analyzer = new XMLAnalyzer();
        saxParser.parse(path,analyzer);
        registeredInventories = analyzer.getRegisteredInventories();
    }

    public ArrayList<AbstractInventory> getRegisteredInventories() {
        return registeredInventories;
    }
}
