package api.Save;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save {


    public static void save(Object object) {


        String fileName = "data.bin";
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

}
