package api.Save;


import java.io.*;

public class Save {

    private static final String fileName = "./res/save.bin";

    public static void save(Object object) {



        ObjectOutputStream os;

        System.out.println("Saving....");
        {
            try {
                os = new ObjectOutputStream(new FileOutputStream(fileName));
                os.writeObject(object);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Save successful");
    }


    public static Object load() {
        Object result = null;
        System.out.println("Loading...");
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
            result = is.readObject();
            is.close();
        } catch (FileNotFoundException e) {
            System.out.println("No save files found. Starting new game...");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Load successful!");
        return result;
    }


}
