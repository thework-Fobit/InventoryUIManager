package fan.frozen.inventoryuimanager.Utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

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
        return IntStream.range(A,B).toArray();
    }

    public static int[] addIntegerElements(int[] original, int... elements){
        int[] array = new int[original.length+elements.length];
        System.arraycopy(original,0,array,0,original.length);
        System.arraycopy(elements,0,array,original.length,elements.length);
        return array;
    }

    public static String[] addStringElements(String[] original, String... elements){
        String[] array = new String[original.length+elements.length];
        System.arraycopy(original,0,array,0,original.length);
        System.arraycopy(elements,0,array,original.length,elements.length);
        return array;
    }

    public static String getColor(boolean flag){
        return flag ? ChatColor.of(new Color(0x3DFA00))+"true" : ChatColor.of(new Color(0xFC0000))+"false";
    }

    public static boolean intArrayContains(int[] original, int element){
        return new ArrayUtils<>(Arrays.stream(original).boxed().toArray(Integer[]::new)).contains(element);
    }

}
