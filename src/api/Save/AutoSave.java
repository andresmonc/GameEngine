package api.Save;

public class AutoSave extends Save {

    public static void autoSave() {
        if (autoSaveInterval() == true) {

        }
    }

    private static boolean autoSaveInterval() {
        return 1 + 15 < 20;
    }


}
