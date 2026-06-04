package dto;

import java.util.List;

public class InGameKnightDetailsDTO {
    private String playerName;
    private String knightName;
    private List<String> skillNames;
    private List<Integer> skillAPs;
    private int knightAP;
    private List<Integer> buffsApplied; // in order: attack, magic attack, defense, speed


    public InGameKnightDetailsDTO(String playerName, String knightName,
                                  List<String> skillNames, List<Integer> skillAPs, int knightAP,
                                  List<Integer> buffsApplied) {
        this.playerName = playerName;
        this.knightName = knightName;
        this.skillNames = skillNames;
        this.skillAPs = skillAPs;
        this.knightAP = knightAP;
        this.buffsApplied = buffsApplied;
    }
}
