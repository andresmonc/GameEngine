package worldEditor;

import java.io.*;

public class SavedEntitiesLoader {


    public static void loadEntitiesFile(String fileName) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File("worldEditor/" + fileName + ".txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't load file!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;

        while (true) {
            try {
                line = reader.readLine();
//                String[] currentLine = line.split(" ");
//                System.out.println(currentLine);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
