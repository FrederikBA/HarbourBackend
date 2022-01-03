package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String brand;
    private String make;
    private String name;
    private String image;

    @ManyToOne
    private Harbour harbour;

    @ManyToMany
    private List<Owner> owners;

    public Boat() {
    }

    public Boat(String brand, String make, String name, String image) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.owners = new ArrayList<>();
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

    public Harbour getHarbour() {
        return harbour;
    }

    public void setHarbour(Harbour harbour) {
        this.harbour = harbour;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
