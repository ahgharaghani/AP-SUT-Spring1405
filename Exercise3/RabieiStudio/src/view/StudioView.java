package view;

import controller.StudioController;
import model.command.StudioCommand;
import model.dto.PlayResultDTO;

import java.util.Scanner;

public class StudioView {
    private static StudioController studioController = StudioController.getInstance();

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String query = scanner.nextLine();

            if (StudioCommand.BUILD_RIG.matches(query)) {
                String channel = StudioCommand.BUILD_RIG.getParameter("channel");
                String pedalsStr = StudioCommand.BUILD_RIG.getParameter("pedals");
                String[] pedals = (pedalsStr == null) ? null : pedalsStr.split("\\s+");

                String result = studioController.buildRig(channel, pedals);
                if (result != null) System.out.println(result);
            } else if (StudioCommand.SET_CHANNEL.matches(query)) {
                String channel = StudioCommand.SET_CHANNEL.getParameter("channel");

                String result = studioController.setChannel(channel);
                if (result != null) System.out.println(result);
            } else if (StudioCommand.PLAY.matches(query)) {
                String riff = StudioCommand.PLAY.getParameter("riff");

                PlayResultDTO result = studioController.playRiff(riff);
                handlePlayResult(result);
            } else if (StudioCommand.EXIT.matches(query)) {
                break;
            } else System.out.println("[ERROR]: Invalid command");
        }
        scanner.close();
    }

    private static void handlePlayResult(PlayResultDTO result) {
        if (result.getErrorMessage() != null) {
            System.out.println(result.getErrorMessage()); return;
        }

        if (result.isBanned()) {
            System.out.println("[WARNING]: Banned riff detected! Stopping playback");
        }

        if (result.getHeatLevel() >= 3) {
            System.out.println("[WARNING]: Amp tubes are running hot! current value: " + result.getHeatLevel());
        }

        if (result.isOverheated()) {
            System.out.println("[SYSTEM]: Amp tubes overheated! Studio powering off.");
        }

        if (!result.isBanned() && !result.isOverheated()) {
            System.out.println(result.getProcessedRiff());
        }
    }
}