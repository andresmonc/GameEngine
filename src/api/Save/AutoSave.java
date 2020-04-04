package api.Save;

import entities.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AutoSave extends Save {

    private LocalDateTime lastSaveTime = LocalDateTime.now();


    public void autoSave(Player player) {
        if (autoSaveInterval()) {
            System.out.println("Auto saving...");
            Save.save(player);
        }
    }

    private boolean autoSaveInterval() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (Duration.between(lastSaveTime, currentTime).getSeconds() > 60) {
            lastSaveTime = currentTime;
            return true;
        }
        return false;
    }

}
