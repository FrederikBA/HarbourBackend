package dtos.Harbour;

import entities.Harbour;

import java.util.List;
import java.util.Objects;

public class HarboursDTO {
    private List<HarbourDTO> harbours;

    public HarboursDTO(List<Harbour> harbours) {
        this.harbours = HarbourDTO.getFromList(harbours);
    }

    public List<HarbourDTO> getHarbours() {
        return harbours;
    }

    public void setHarbours(List<HarbourDTO> harbours) {
        this.harbours = harbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarboursDTO that = (HarboursDTO) o;
        return Objects.equals(harbours, that.harbours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(harbours);
    }

    @Override
    public String toString() {
        return "HarboursDTO{" +
                "harbours=" + harbours +
                '}';
    }
}
