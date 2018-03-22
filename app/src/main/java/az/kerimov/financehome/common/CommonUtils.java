package az.kerimov.financehome.common;

import java.util.HashMap;
import java.util.List;

public class CommonUtils {

    public static void fillMap(HashMap map, List list){
        map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));
        }
    }
    public static HashMap fillMap(List list){
        HashMap map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));
        }
        return map;
    }
/*
    public String[] adapterArray(HashMap map, List list){

        String[] result = new String[list.size()];
        map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(i, list.get(i));
            result[i] = list.get(i).getName();
        }

    }
    */

}
