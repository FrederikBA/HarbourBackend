package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String address;
    private String phone;

    @ManyToMany(mappedBy = "owners", cascade = CascadeType.PERSIST)
    private List<Boat> boats;

    public Owner() {
    }

    public Owner(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boats = new ArrayList<>();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    public void addBoat(Boat boat) {
        if (boat != null) {
            this.boats.add(boat);
            boat.getOwners().add(this);
        }
    }
}
