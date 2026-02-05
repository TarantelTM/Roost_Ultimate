package net.tarantel.chickenroost;

public class JEIUtils {
    private JEIUtils() {}

    public static boolean isJEIAvailable() {
        try {
            Class.forName("mezz.jei.api.IModPlugin");

            return true;
        }catch(ClassNotFoundException e) {
            return false;
        }
    }
}