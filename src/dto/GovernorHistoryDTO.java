package dto;

import model.Farm;

public class GovernorHistoryDTO {
    public final int id;
    public final String name;
    public final String ideology;
    public final int day;

    public GovernorHistoryDTO(Farm.GovernorRecord record) {
        this.id = record.id;
        this.name = record.name;
        this.ideology = record.system.toString().toLowerCase();
        this.day = record.startingDay;
    }
}
