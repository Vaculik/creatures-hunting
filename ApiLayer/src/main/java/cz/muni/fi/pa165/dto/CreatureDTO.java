package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.CreatureType;

/**
 * Created by vaculik on 20.11.15.
 */
public class CreatureDTO {

    private Long id;
    private String name;
    private Integer height;
    private Integer weight;
    private CreatureType type;
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
        if (this == o) return true;
        if (!(o instanceof CreatureDTO)) return false;

        CreatureDTO that = (CreatureDTO) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getHeight() != null ? !getHeight().equals(that.getHeight()) : that.getHeight() != null) return false;
        if (getWeight() != null ? !getWeight().equals(that.getWeight()) : that.getWeight() != null) return false;
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
}

