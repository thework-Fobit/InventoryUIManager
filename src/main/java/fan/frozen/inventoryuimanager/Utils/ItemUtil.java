package fan.frozen.inventoryuimanager.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ItemUtil {
    public static ItemStack getItemWithName(Material material,String name){
        ItemStack itemStack = new ItemStack(material);
        changeItemName(itemStack,name);
        return itemStack;
    }
    public static void changeItemName(ItemStack itemStack,String name){
        ItemMeta itemMeta = getItemMeta(itemStack);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
    }
    public static void changeItemLore(ItemStack itemStack,String... lore){
        ArrayList<String> Lore = new ArrayList<>();
        Collections.addAll(Lore,lore);
        ItemMeta itemMeta = getItemMeta(itemStack);
        itemMeta.setLore(Lore);
        itemStack.setItemMeta(itemMeta);
    }
    public static ItemMeta getItemMeta(ItemStack itemStack){
        return Objects.requireNonNullElseGet(itemStack, () -> new ItemStack(Material.BARRIER)).getItemMeta();
    }
}
