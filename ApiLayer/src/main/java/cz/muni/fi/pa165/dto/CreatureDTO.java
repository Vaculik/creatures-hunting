package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.CreatureType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transfer object of the Creature entity.
 *
 * @author Karel Vaculik
 */
public class CreatureDTO {

    private Long id;

    @NotNull
    @Size(min=3, max=64)
    private String name;

    @NotNull
    @Min(1)
    private Integer height;

    @NotNull
    @Min(1)
    private Integer weight;

    @NotNull
    private CreatureType type;

    @Size(max=255)
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;

    }

    public CreatureType getType() {
        return type;
    }

    public void setType(CreatureType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatureDTO)) {
            return false;
        }

        CreatureDTO that = (CreatureDTO) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (getHeight() != null ? !getHeight().equals(that.getHeight()) : that.getHeight() != null) {
            return false;
        }
        if (getWeight() != null ? !getWeight().equals(that.getWeight()) : that.getWeight() != null) {
            return false;
        }
        return getType() == that.getType();

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getHeight() != null ? getHeight().hashCode() : 0);
        result = 31 * result + (getWeight() != null ? getWeight().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreatureDTO{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", height=" + height
                + ", weight=" + weight
                + ", type=" + type
                + '}';
    }
}
