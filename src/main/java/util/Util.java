package util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Util {


   public static void compare(List<Long> list1, List<Long> list2){
       Collection<Long> similar = new HashSet<>( list1 );
       Collection<Long> different = new HashSet<>();

       different.addAll( list1 );
       different.addAll( list2 );

       similar.retainAll( list2 );
       different.removeAll( similar );

       System.out.println("count of similar ids "+ similar.size());
       System.out.println("count of different  ids "+ different.size());
   }


    public static void main(String[] args) {
       List<Long> list1 = Arrays.asList(1l,2l,3l,4l);
       List<Long> list2 = Arrays.asList(1l,2l,3l);
        compare(list1,list2);
    }

}
