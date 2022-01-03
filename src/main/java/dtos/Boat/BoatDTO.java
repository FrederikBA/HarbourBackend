package dtos.Boat;

import dtos.Harbour.HarbourDTO;
import dtos.Owner.OwnerDTO;
import entities.Boat;
import entities.Owner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BoatDTO {
    private int id;
    private String brand;
    private String make;
    private String name;
    private String image;
    private List<OwnerDTO> owners;

    public static List<BoatDTO> getFromList(List<Boat> boats) {
        return boats.stream()
                .map(boat -> new BoatDTO(boat))
                .collect(Collectors.toList());
    }

    public BoatDTO(Boat boat) {
        this.id = boat.getId();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.name = boat.getName();
        this.image = boat.getImage();
        this.owners = OwnerDTO.getFromList(boat.getOwners());
    }

    public BoatDTO(int id, String brand, String make, String name, String image, List<OwnerDTO> owners) {
        this.id = -1;
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.owners = owners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<OwnerDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnerDTO> owners) {
        this.owners = owners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoatDTO boatDTO = (BoatDTO) o;
        return id == boatDTO.id && Objects.equals(brand, boatDTO.brand) && Objects.equals(make, boatDTO.make) && Objects.equals(name, boatDTO.name) && Objects.equals(image, boatDTO.image) && Objects.equals(owners, boatDTO.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, make, name, image, owners);
    }

    @Override
    public String toString() {
        return "BoatDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", owners=" + owners +
                '}';
    }
}
