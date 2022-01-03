package dtos.Owner;

import entities.Owner;

import java.util.List;
import java.util.Objects;

public class OwnersDTO {
    private List<OwnerDTO> owners;

    public OwnersDTO(List<Owner> owners) {
        this.owners = OwnerDTO.getFromList(owners);
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
        OwnersDTO ownersDTO = (OwnersDTO) o;
        return Objects.equals(owners, ownersDTO.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owners);
    }

    @Override
    public String toString() {
        return "OwnersDTO{" +
                "owners=" + owners +
                '}';
    }
}
