package dto;

public class InGameKnightStatsDTO {
    private String knightName;
    private String knightClass;
    private int hp;
    private int attack;
    private int magicAttack;
    private int defense;
    private int speed;

    public InGameKnightStatsDTO(String knightName, String knightClass, int hp, int attack, int magicAttack, int defense, int speed) {
        this.knightName = knightName;
        this.knightClass = knightClass;
        this.hp = hp;
        this.attack = attack;
        this.magicAttack = magicAttack;
        this.defense = defense;
        this.speed = speed;
    }
}
