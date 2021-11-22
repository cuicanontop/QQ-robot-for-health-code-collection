package love.simbot.example.util;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

public class H2Util {
    static MVStore db=MVStore.open("CuicanBot.db");

    public static String Gettableitem(String t,Long k ){

        MVMap<Long, String> map= db.openMap(t);
       String I =  map.get(k);

        return I;
    }


    public static String Gettableitem(String t,String k ){
        MVMap<String, String> map= db.openMap(t);
        String I =  map.get(k);

        return I;
    }

    public static void Settableitem(String t,Long k,String V ){
        MVMap<Long, String> map= db.openMap(t);
        map.put(k,V);

    }
    public static void Deltableitem(String t,Long k ){
        MVMap<Long, String> map= db.openMap(t);
        map.remove(k);


    }

}
