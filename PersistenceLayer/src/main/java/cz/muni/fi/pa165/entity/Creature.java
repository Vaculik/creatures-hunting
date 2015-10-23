package cz.muni.fi.pa165.entity;


import cz.muni.fi.pa165.enums.CreatureType;

import javax.persistence.*;

/**
 * Created by vaculik on 23.10.15.
 */

@Entity
@Table(name = "CREATURE_TABLE")
public class Creature {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer height;

    private Integer weight;

    @Enumerated
    private CreatureType type;

    private String description;

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
        hash = 23 * hash + ((type != null ) ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Creature)) {
            return false;
        }
        Creature creature = (Creature) obj;

        if (creature.name == null) {
            if (this.name != null) {
                return false;
            }

        } else if ( !creature.name.equals(this.name)) {
            return false;
        }

        if (creature.height == null) {
            if (this.height != null) {
                return false;
            }
        } else if ( !creature.height.equals(this.height)) {
            return false;
        }

        if (creature.weight == null) {
            if (this.weight != null) {
                return false;
            }
        } else if ( !creature.weight.equals(this.weight)) {
            return false;
        }

        if (creature.type != this.type) {
            return false;
        }

        return true;
    }
}
