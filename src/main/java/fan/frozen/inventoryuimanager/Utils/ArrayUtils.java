package fan.frozen.inventoryuimanager.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils<T>{
    private T[] type;

    public ArrayUtils(T[] type){
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public ArrayUtils<?> addElement(T element){
        Object[] objectInstance = new Object[type.length+1];
        objectInstance[objectInstance.length-1] = element;
        type = (T[])objectInstance;
        return this;
    }

    @SuppressWarnings("unchecked")
    public ArrayUtils<?> addElements(T[] elements){
        Object[] objectInstance = new Object[type.length+elements.length];
        System.arraycopy(type,0,objectInstance,0,type.length);
        System.arraycopy(elements,0,objectInstance,type.length,objectInstance.length);
        type = (T[])objectInstance;
        return this;
    }

    public boolean contains(T element){
        return Arrays.stream(type).anyMatch(i -> i==element);
    }

    public List<T> getAsList(){
        return Arrays.stream(type).toList();
    }

    public T[] getArray(){
        return type;
    }
}
