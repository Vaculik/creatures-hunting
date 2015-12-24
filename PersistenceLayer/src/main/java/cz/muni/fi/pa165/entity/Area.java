/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This entity represents an area (a location} of the world. This entity
 * includes set of creatures.
 *
 * @author Martin Zboril
 */
@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    String name;
    
    String description;
    
    // Mapping by Id causes Creature to crash on persist
    @OneToMany(mappedBy = "area")
    List<Creature> creatures = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id is less than 0");
        }
        if (id == null) {
            throw new NullPointerException("Input Id is null");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Input Name is null");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("Name has no length");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name is too long");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }    

    public void addCreature(Creature creature) {
        creatures.add(creature);
        creature.setArea(this);
    }
    
    public void removeCreature(Creature creature) {
        creatures.remove(creature);
        creature.setArea(null);
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((creatures == null) ? 0 : creatures.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Area other = (Area) obj;
        if (creatures == null) {
            if (other.creatures != null) {
                return false;
            }
        } else if (!creatures.equals(other.creatures)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Area: " + "id=" + id + ", name=" + name;
    }
}
