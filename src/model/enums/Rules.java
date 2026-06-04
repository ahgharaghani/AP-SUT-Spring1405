package model.enums;

import model.ideology.Ideology;

public enum Rules {
    RULE1(1, "Whatever goes upon two legs is an enemy.", "dictatorship"),
    RULE2(2, "Whatever goes upon four legs, or has wings, is a friend.", "democracy"),
    RULE3(3, "No animal shall wear clothes.", "communism"),
    RULE4(4, "No animal shall sleep in a bed.", "communism"),
    RULE5(5, "No animal shall drink alcohol.", "communism"),
    RULE6(6, "No animal shall kill any other animal.", "democracy"),
    RULE7(7, "All animals are equal.", "communism");

    private int ID;
    private String description;
    private IdeologyType ideology;


    Rules(int ID, String description, String ideology) {
        this.ID = ID;
        this.description = description;
        this.ideology = IdeologyType.ofString(ideology);
    }

    public int getID() { return ID; }
    public String getDescription() { return description; }
    public IdeologyType getIdeology() { return ideology; }

    public void changeRule(String newDescription, IdeologyType newIdeology) {
        description = newDescription;
        ideology = newIdeology;
    }
}
