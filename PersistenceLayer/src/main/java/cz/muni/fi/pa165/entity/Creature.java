package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.CreatureType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The entity represents a creature which we want to track and holds some basic
 * information about it like name, height, weight or type.
 *
 * @author Karel Vaculik
 */
@Entity
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    
    @NotNull
    private Integer height;
    
    @NotNull
    private Integer weight;
    
    @Enumerated
    @NotNull
    private CreatureType type;
    
    // Parameter description is not relevant for distinguishing two creatures
    private String description;

    public Creature() {
    }

    public Creature(Long id) {
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

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setType(CreatureType type) {
        this.type = type;
    }

    public CreatureType getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 17 * hash + ((name != null) ? name.hashCode() : 0);
        hash = 13 * hash + ((height != null) ? height.hashCode() : 0);
        hash = 19 * hash + ((weight != null) ? weight.hashCode() : 0);
        hash = 23 * hash + ((type != null) ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Creature)) {
            return false;
        }
        Creature other = (Creature) obj;

        if (other.getName() == null) {
            if (this.name != null) {
                return false;
            }

        } else if (!other.getName().equals(this.name)) {
            return false;
        }

        if (other.getHeight() == null) {
            if (this.height != null) {
                return false;
            }
        } else if (!other.getHeight().equals(this.height)) {
            return false;
        }

        if (other.getWeight() == null) {
            if (this.weight != null) {
                return false;
            }
        } else if (!other.getWeight().equals(this.weight)) {
            return false;
        }

        if (other.getType() != this.type) {
            return false;
        }

        return true;
    }
}
