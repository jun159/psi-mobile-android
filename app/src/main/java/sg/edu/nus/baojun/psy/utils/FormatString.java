package sg.edu.nus.baojun.psy.utils;

/**
 * Created by BAOJUN on 9/9/17.
 */

public class FormatString {

    private FormatString() {
        // Prevent instantiation
    }

    public static String camelCase(String s) {
        if(s != null && !s.isEmpty()) {
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }

        return s;
    }
}
