/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a Data Transfer Object of Area.
 * @author Martin Zboril
 */
public class AreaDTO {

    Long id;
    String name;
    String description;
    Set<CreatureDTO> creatures = new HashSet<CreatureDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CreatureDTO> getCreatures() {
        return Collections.unmodifiableSet(creatures);
    }

    public void setCreatures(Set<CreatureDTO> creatures) {
        this.creatures = creatures;
    }
    
    public void addCreature(CreatureDTO creature){
        creatures.add(creature);
    }        
    
    public void removeCreature(CreatureDTO creature){
        creatures.remove(creature);
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
        AreaDTO other = (AreaDTO) obj;
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