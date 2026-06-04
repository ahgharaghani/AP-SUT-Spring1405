package model.pedal;

public abstract class EffectNode {
    protected EffectNode nextNode;

    public EffectNode() {
        nextNode = null;
    }

    public void setNextNode(EffectNode nextNode) {
        this.nextNode = nextNode;
    }

    public abstract String effect(String riff);
}
