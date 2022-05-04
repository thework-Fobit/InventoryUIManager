package fan.frozen.inventoryuimanager.Utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;

/**
 * utils which used to process array and chat color
 */
public class CommonUtil {
    public static int[] getIntArrayFromAToB(int A,int B) {
        if (B<A){
            System.out.println("B must bigger than A");
            return new int[0];
        } else if (B==A) {
            return new int[]{A};
        }
        int[] array = new int[(B-A)+1];
        int counter = 0;
        for (int i = A; i <=B ; i++) {
            array[counter] = i;
            counter++;
        }
        return array;
    }
    public static int[] addElements(int[] original,int... elements){
        int[] array = new int[original.length+elements.length];
        System.arraycopy(original,0,array,0,original.length);
        System.arraycopy(elements,0,array,original.length,elements.length);
        return array;
    }
    public static String getColor(boolean flag){
        if (flag){
            return ChatColor.of(new Color(0x3DFA00))+"true";
        }else {
            return ChatColor.of(new Color(0xFC0000))+"false";
        }
    }
}
