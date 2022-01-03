package dtos.Owner;

import dtos.Role.RoleDTO;
import entities.Owner;
import entities.Role;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OwnerDTO {
    private int id;
    private String name;
    private String address;
    private String phone;

    public static List<OwnerDTO> getFromList(List<Owner> owners) {
        return owners.stream()
                .map(owner -> new OwnerDTO(owner))
                .collect(Collectors.toList());
    }

    public OwnerDTO(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
    }

    public OwnerDTO(String name, String address, String phone) {
        this.id = -1;
        this.name = name;
        this.address = address;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDTO ownerDTO = (OwnerDTO) o;
        return id == ownerDTO.id && Objects.equals(name, ownerDTO.name) && Objects.equals(address, ownerDTO.address) && Objects.equals(phone, ownerDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone);
    }

    @Override
    public String toString() {
        return "OwnerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
