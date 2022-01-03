package dtos.Harbour;

import entities.Harbour;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HarbourDTO {
    private Integer id;
    private String name;
    private String address;
    private int capacity;

    public static List<HarbourDTO> getFromList(List<Harbour> harbours) {
        return harbours.stream()
                .map(harbour -> new HarbourDTO(harbour))
                .collect(Collectors.toList());
    }

    public HarbourDTO(Harbour harbour) {
        this.id = harbour.getId();
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.capacity = harbour.getCapacity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarbourDTO that = (HarbourDTO) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, capacity);
    }

    @Override
    public String toString() {
        return "HarbourDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
