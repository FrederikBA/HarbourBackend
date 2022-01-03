package dtos.Harbour;

import dtos.Boat.BoatDTO;
import entities.Boat;
import entities.Harbour;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HarbourDTO {
    private int id;
    private String name;
    private String address;
    private int capacity;
    private List<BoatDTO> boats;

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
        this.boats = BoatDTO.getFromList(harbour.getBoats());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(List<BoatDTO> boats) {
        this.boats = boats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarbourDTO that = (HarbourDTO) o;
        return id == that.id && capacity == that.capacity && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(boats, that.boats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, capacity, boats);
    }

    @Override
    public String toString() {
        return "HarbourDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", boats=" + boats +
                '}';
    }
}
