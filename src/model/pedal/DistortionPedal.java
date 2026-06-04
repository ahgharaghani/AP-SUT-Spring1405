package model.pedal;

public class DistortionPedal extends EffectNode {
    @Override
    public String effect(String riff) {
        if (nextNode != null) return nextNode.effect(riff + " \\m/");
        return riff + " \\m/";
    }
}