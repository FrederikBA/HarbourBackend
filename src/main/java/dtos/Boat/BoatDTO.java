package dtos.Boat;

import dtos.Harbour.HarbourDTO;
import dtos.Owner.OwnerDTO;
import entities.Boat;
import entities.Owner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BoatDTO {
    private Integer id;
    private String brand;
    private String make;
    private String name;
    private String image;
    private List<OwnerDTO> owners;
    private HarbourDTO harbour;

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
        this.harbour = new HarbourDTO(boat.getHarbour());
    }

    public BoatDTO(String brand, String make, String name, String image) {
        this.id = -1;
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public HarbourDTO getHarbour() {
        return harbour;
    }

    public void setHarbour(HarbourDTO harbour) {
        this.harbour = harbour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoatDTO boatDTO = (BoatDTO) o;
        return Objects.equals(id, boatDTO.id) && Objects.equals(brand, boatDTO.brand) && Objects.equals(make, boatDTO.make) && Objects.equals(name, boatDTO.name) && Objects.equals(image, boatDTO.image) && Objects.equals(owners, boatDTO.owners) && Objects.equals(harbour, boatDTO.harbour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, make, name, image, owners, harbour);
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
                ", harbour=" + harbour +
                '}';
    }
}
