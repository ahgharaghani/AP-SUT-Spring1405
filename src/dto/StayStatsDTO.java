package dto;

public class StayStatsDTO {
    public int id;
    public String name;
    public String city;
    public String address;
    public int cap;
    public int ppn;
    public String policy;
    public String status;

    public StayStatsDTO(int id, String name, String city, String address, int cap, int ppn, String policy, String status) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.cap = cap;
        this.ppn = ppn;
        this.policy = policy;
        this.status = status;
    }
}
