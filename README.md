# InventoryUIManager
its a developing minecraft spigot API which you can use it to create and manage inventory UI easily
# how to get access to IUM
 you can access IUM by maven<br>
 example down below shows how you can add IUM into your maven project as a dependency
```xml
<dependency>
  <groupId>fan.frozen</groupId>
  <artifactId>InventoryUIManager</artifactId>
  <version>1.2-SNAPSHOT</version>
</dependency>
```
# how can I use IUM
## User
use command
```text
/uimanager
```
to open manage page
## Developer
### create inventory
1. create a singlePage inventory
```java
public class Example {
    public void createInventory(){
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54) //you can fill it with an already existed spigot inventory object
                ,"singlePageInventory" //the name of the inventory
                , InventoryUIManager.getProvidingPlugin(InventoryUIManager.class) //your own plugin object
        );
    }
}
```
2. create a multiple pages inventory
```java
public class Example {
    public void createInventory(){
        MultiPageInventory multiPageInventory = new MultiPageInventory(
                "multiPageInventory"
                ,InventoryUIManager.getProvidingPlugin(InventoryUIManager.class)
                ,Bukkit.createInventory(null,54)//page one
                ,Bukkit.createInventory(null,54)//page two
                //you can add as many pages as you want
        );
    }
}
```
3. create a node inventory
```java
public class Example {
    public void createInventory(){
        SinglePageInventory upperInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54,"singlePageInventory")
                ,"upperInventory"
                ,InventoryUIManager.getProvidingPlugin(InventoryUIManager.class)
        );
        SinglePageInventory currentInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54,"singlePageInventory")
                ,"currentInventory"
                ,InventoryUIManager.getProvidingPlugin(InventoryUIManager.class)
        );
        NodeInventory nodeInventory = new NodeInventory(
                upperInventory //upper node inventroy which can be null (can be any object which extend AbstractInventory)
                ,null //lower node inventory (also can be null)
                ,currentInventory //the inventory in current node CAN'T BE NULL !!!
        );
    }
}
```
4. create an anvil inventory
```java
public class Example {
    public void createInventory(Player player){
       AnvilInventory anvilInventory = AnvilInventory.getInstance(
               player //player who will use this inventory
               ,"AnvilInventory"
               ,InventoryUIManager.getProvidingPlugin(InventoryUIManager.class)
       );
    }
}
```
**to be noticed, pleas use getInstance(Player,String,Plugin) to get anvil instance instead of using constructor to construct**
### handle invnetory events
we provide very easy access to the inventory events
now if you want to listen to a inventory click event you can do this
1. first create a handler class which extends to InventoryClickEventHandler
```java
public class HandlerExample extends InventoryClickEventHandler {
    public HandlerExample(AbstractInventory abstractInventory, Plugin plugin) {
        super(abstractInventory, plugin);
    }

    @Override
    public void onActive(InventoryClickEvent event) {
        //write your running logic in here
    }
}
```
2. second register this handler to an abstractInventory
```java
public class HandlerRegistrationExample {
    public void registerHandlerToAbsInventory(){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54)
                ,"singlePageInventory"
                , providingPlugin
        );
        singlePageInventory.registerHandler(new HandlerExample(singlePageInventory,providingPlugin));
    }
}
```
or you can do it in reflection way
```java
public class HandlerRegistrationExample {
    public void registerHandlerToAbsInventory(){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54)
                ,"singlePageInventory"
                , providingPlugin
        );
        singlePageInventory.registerHandler(HandlerExample.class);
    }
}
```
***
or you can do it in a much easier way like this
you don't have to create another obejct which extend InventoryClickEventHandler
```java
public class HandlerRegistrationExample {
    public void registerHandlerToAbsInventory(){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54)
                ,"singlePageInventory"
                , providingPlugin
        );
        singlePageInventory.registerHandler(
                new InventoryClickEventHandler(singlePageInventory.getAbsInventory(),providingPlugin)
        {
            @Override
            public void onActive(InventoryClickEvent event) {
                //your running logic
            }
        });
    }
}
```
### components and its registrations
if you think is annoy for you to write your own running logic
we also provide you some components you can use
1. Button
```java
public class ComponentsAndRegistrationExample {
    public void registerComponentToAbsInventory(){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54)
                ,"singlePageInventory"
                , providingPlugin
        );
        Button button = new Button(
                "button" //the name of your component
                ,new ItemStack(Material.STONE_BUTTON) //what kind of item you want your component be in the inventory
                ,10 // the location of the components in the inventory(in original spigot inventory this number range in 0 to 53(The biggest inventory))
        ) {
            @Override
            public void activeOnTrigger(InventoryClickEvent event) {
                //running logic, which will be trigger by player click
            }
        };
        singlePageInventory.registerComponent(button);
    }
}
```
also you can extend and override Button class to customize your own components
2. Border
```java
public class ComponentsAndRegistrationExample {
    public void registerComponentToAbsInventory(){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54)
                ,"singlePageInventory"
                , providingPlugin
        );
        Border border = BorderPreset.AROUND_BORDER.getBorder(
                singlePageInventory //inventory you want it deploy at
                ,false //ignore components
                ,new ItemStack(Material.BARRIER) //the material of the border
                ,54 //your inventory size
        );        
        singlePageInventory.registerComponent(border);
        border.deployBorder();
    }
}
```
3. Lable
```java
public class ComponentsAndRegistrationExample {
    public void registerComponentToAbsInventory(){
        JavaPlugin providingPlugin = InventoryUIManager.getProvidingPlugin(InventoryUIManager.class);
        SinglePageInventory singlePageInventory = new SinglePageInventory(
                Bukkit.createInventory(null,54)
                ,"singlePageInventory"
                , providingPlugin
        );
        Label label = new Label(
                "label"
                ,new ItemStack(Material.PAPER)//the material of the label
                ,10// the location will show in the inventory
                ,"hello world"// the text you want it to display
                ,"I love coding in java"
        );
        singlePageInventory.registerComponent(label);
    }
}
```
***
we still have many components' development on our develop schedule
if you have any good idea, please let us know
### JavaDoc
get JavaDoc at [here](https://drive.google.com/file/d/15rAGHOSWpo8PpPh25ugEAdXsgohoKIfP/view?usp=sharing)
