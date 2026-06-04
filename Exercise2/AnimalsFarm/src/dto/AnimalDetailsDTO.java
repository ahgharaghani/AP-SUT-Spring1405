package dto;

import java.util.Map;

public class AnimalDetailsDTO {
    private int ID;
    private String name;
    private String type;
    private String role;

    private double fullness;

    private boolean rebellious;
    private boolean liar;
    private boolean corrupt;
    private boolean lazy;

    private Map<String, Double> politicalOpinion;
    private Map<String, Double> animalTypeOpinion;

    public AnimalDetailsDTO(int ID, String name, String type, String role,
                            double fullness, boolean rebellious, boolean liar, boolean corrupt, boolean lazy,
                            Map<String, Double> politicalOpinion, Map<String, Double> animalTypeOpinion) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.role = role;
        this.fullness = fullness;
        this.rebellious = rebellious;
        this.liar = liar;
        this.corrupt = corrupt;
        this.lazy = lazy;
        this.politicalOpinion = politicalOpinion;
        this.animalTypeOpinion = animalTypeOpinion;
    }

    public int getID() { return ID; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getRole() { return role; }
    public double getFullness() { return fullness; }
    public boolean isRebellious() { return rebellious; }
    public boolean isLiar() { return liar; }
    public boolean isCorrupt() { return corrupt; }
    public boolean isLazy() { return lazy; }
    public Map<String, Double> getPoliticalOpinion() { return politicalOpinion; }
    public Map<String, Double> getAnimalTypeOpinion() { return animalTypeOpinion; }
}
