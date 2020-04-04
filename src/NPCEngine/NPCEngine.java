package NPCEngine;

import entities.NPC.NPC;

import java.util.ArrayList;
import java.util.List;

public class NPCEngine {

    private List<NPC> npcList = new ArrayList<>();

    public void addNpc(NPC npc) {
        npcList.add(npc);
    }

    public List<NPC> getNpcList() {
        return npcList;
    }

    public void beginRandomizedMovement(){
        npcList.forEach(npc -> {
            npc.setCurrentSpeed(50);
            npc.setCurrentTurnSpeed(140);
        });
    }

}
