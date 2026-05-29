package model.dto;

public class PlayResultDTO {
    private String errorMessage;
    private boolean isBanned;
    private int heatLevel;
    private boolean isOverheated;
    private String processedRiff;

    public PlayResultDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PlayResultDTO() {
        this.isBanned = false;
        this.heatLevel = -1;
        this.isOverheated = false;
    }

    public void setBanned(boolean isBanned) { this.isBanned = isBanned; }
    public void setHeatLevel(int level) { this.heatLevel = level; }
    public void setOverheated(boolean isOverheated) { this.isOverheated = isOverheated; }
    public void setProcessedRiff(String processedRiff) { this.processedRiff = processedRiff; }

    public String getErrorMessage() { return errorMessage; }
    public boolean isBanned() { return isBanned; }
    public int getHeatLevel() { return heatLevel; }
    public boolean isOverheated() { return isOverheated; }
    public String getProcessedRiff() { return processedRiff; }
}