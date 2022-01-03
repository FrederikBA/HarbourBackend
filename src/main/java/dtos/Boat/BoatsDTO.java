package dtos.Boat;

import entities.Boat;

import java.util.List;
import java.util.Objects;

public class BoatsDTO {
    private List<BoatDTO> boats;

    public BoatsDTO(List<Boat> boats) {
        this.boats = BoatDTO.getFromList(boats);
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
        BoatsDTO boatsDTO = (BoatsDTO) o;
        return Objects.equals(boats, boatsDTO.boats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boats);
    }

    @Override
    public String toString() {
        return "BoatsDTO{" +
                "boats=" + boats +
                '}';
    }
}
