package controller;

import model.GuitarRig;
import model.dto.PlayResultDTO;
import model.RigBuilder;
import model.StudioSession;
import model.channel.CleanChannel;
import model.channel.CrunchChannel;
import model.channel.LeadChannel;
import model.observer.BannedRiffObserver;
import model.observer.TubeAmpHeatMonitor;
import model.pedal.DelayPedal;
import model.pedal.DistortionPedal;
import model.pedal.EQPedal;

public class StudioController {
    private static StudioController instance = null;
    private StudioSession session;
    private GuitarRig rig;

    private StudioController() {
        this.session = StudioSession.getInstance();
        session.attachEngineer(new BannedRiffObserver());
        session.attachEngineer(new TubeAmpHeatMonitor());
        this.rig = null;
    }

    public static StudioController getInstance() {
        if (instance == null) instance = new StudioController();
        return instance;
    }

    public String buildRig(String channel, String[] pedals) {
        RigBuilder rb = new RigBuilder();

        switch (channel) {
            case "CLEAN":
                rb.setChannel(new CleanChannel()); break;
            case "CRUNCH":
                rb.setChannel(new CrunchChannel()); break;
            case "LEAD":
                rb.setChannel(new LeadChannel()); break;
            default:
                return "[ERROR]: Invalid channel";
        }

        for (String pedal : pedals) {
            switch (pedal) {
                case "DISTORTION":
                    rb.addPedal(new DistortionPedal()); break;
                case "EQ":
                    rb.addPedal(new EQPedal()); break;
                case "DELAY":
                    rb.addPedal(new DelayPedal()); break;
                default:
                    return "[ERROR]: Invalid pedal";
            }
        }

        this.rig = rb.build();
        session.resetAmpHeatMonitor();

        return null;
    }

    public String setChannel(String channel) {
        switch (channel) {
            case "CLEAN":
                rig.setChannel(new CleanChannel()); break;
            case "CRUNCH":
                rig.setChannel(new CrunchChannel()); break;
            case "LEAD":
                rig.setChannel(new LeadChannel()); break;
            default:
                return "[ERROR]: Invalid channel";
        }

        return null;
    }

    public PlayResultDTO playRiff(String riff) {
        if (rig == null) return new PlayResultDTO("[ERROR]: No rig built yet");

        if (!session.isPowerOn()) return new PlayResultDTO("[SYSTEM]: Studio power is off. KHIALET RAHAT");

        return session.playRiff(rig, riff);
    }
}
