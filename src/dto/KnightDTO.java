package dto;

import java.util.List;

public class KnightDTO {
    private String name;
    private String classType;
    private int hp;
    private int attack;
    private int magic;
    private int defense;
    private int speed;
    private List<String> skills;

    public KnightDTO(String name, String classType, int hp, int attack, int magic, int defense, int speed, List<String> skills) {
        this.name = name;
        this.classType = classType;
        this.hp = hp;
        this.attack = attack;
        this.magic = magic;
        this.defense = defense;
        this.speed = speed;
        this.skills = skills;
    }

    public String getName() { return name; }
    public String getClassType() { return classType; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getMagic() { return magic; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public List<String> getSkills() { return skills; }
}
