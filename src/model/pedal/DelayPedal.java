package model.pedal;

public class DelayPedal extends EffectNode {
    @Override
    public String effect(String riff) {
        String[] words = riff.split("\\s+");
        String last = words[words.length - 1];

        if (nextNode != null) return nextNode.effect(riff + " " + last);
        return riff + " " + last;
    }
}
