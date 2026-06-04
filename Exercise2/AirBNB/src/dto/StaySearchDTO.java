package dto;

public class StaySearchDTO {
    public int num;
    public String name;
    public int ppn;
    public String hostBrand;

    public StaySearchDTO(int num, String name, int ppn, String hostBrand) {
        this.num = num;
        this.name = name;
        this.ppn = ppn;
        this.hostBrand = hostBrand;
    }
}
