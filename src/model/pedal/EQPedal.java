package model.pedal;

public class EQPedal extends EffectNode {
    @Override
    public String effect(String riff) {
        if (nextNode != null) return nextNode.effect(riff.replaceAll("[aeiouAEIOU]", ""));
        return riff.replaceAll("[aeiouAEIOU]", "");
    }
}
