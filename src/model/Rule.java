package model;

import model.enums.IdeologyType;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    public static List<Rule> defaultRules() {
        List<Rule> list = new ArrayList<>();
        list.add(new Rule("Whatever goes upon two legs is an enemy.", IdeologyType.DICTATORSHIP));
        list.add(new Rule("Whatever goes upon four legs, or has wings, is a friend.", IdeologyType.DEMOCRACY));
        list.add(new Rule("No animal shall wear clothes.", IdeologyType.COMMUNISM));
        list.add(new Rule("No animal shall sleep in a bed.", IdeologyType.COMMUNISM));
        list.add(new Rule("No animal shall drink alcohol.", IdeologyType.COMMUNISM));
        list.add(new Rule("No animal shall kill any other animal.", IdeologyType.DEMOCRACY));
        list.add(new Rule("All animals are equal.", IdeologyType.COMMUNISM));
        return list;
    }

    private String description;
    private IdeologyType ideology;

    public Rule(String description, IdeologyType ideology) {
        this.description = description;
        this.ideology = ideology;
    }

    public String getDescription() { return description; }
    public IdeologyType getIdeology() { return ideology; }

    public void changeRule(String newDescription, IdeologyType newIdeology) {
        this.description = newDescription;
        this.ideology = newIdeology;
    }
}