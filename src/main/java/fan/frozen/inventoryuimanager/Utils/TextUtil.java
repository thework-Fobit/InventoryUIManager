package fan.frozen.inventoryuimanager.Utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public class TextUtil {
    public static String getTag(String context){
        return ChatColor.of(Color.darkGray)+""+org.bukkit.ChatColor.ITALIC+context;
    }
//    ChatColor.of(new Color(0x61C75))+
}
