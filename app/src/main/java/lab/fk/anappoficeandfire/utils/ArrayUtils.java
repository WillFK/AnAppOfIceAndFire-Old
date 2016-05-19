package lab.fk.anappoficeandfire.utils;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

public class ArrayUtils {

    public static String listToString(List<String> list) {
        return listToString(list, ";");
    }

    public static String listToString(List<String> list, String separator) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Stream.of(list).forEach( s -> sb.append(s).append(separator));
        return sb.toString();
    }

}
