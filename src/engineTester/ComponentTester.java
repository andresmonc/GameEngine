package engineTester;

import api.Save.Save;
import entities.Monster;

public class ComponentTester {


    public static void main(String[] args) {
        Monster werewolf = new Monster("Werewolf", 100, 10);
        Save.save(werewolf);

    }
}
