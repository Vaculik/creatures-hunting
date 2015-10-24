/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Martin Zboril
 */
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    
    @NotNull
    @Column(nullable=false,unique=true)
    String name;
    
    String description;
        
    @OneToMany(mappedBy = "id")
    Set<Creature> creatures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id < 0) throw new IllegalArgumentException("Id is less than 0");
        if(id == null) throw new NullPointerException("Input Id is null");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null) throw new NullPointerException("Input Name is null");
        if(name.length()==0) throw new IllegalArgumentException("Name has no length");
        if(name.length()>50) throw new IllegalArgumentException("Name is too long");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(Set<Creature> creatures) {
        this.creatures = creatures;
    }

    @Override
    public int hashCode() {
        int hash = 97;        
        int value1 = creatures != null ? creatures.hashCode() : hash;
        int value2 = name != null ? name.hashCode() : hash;        
        int resultHash = 97 * (value1 % 31) * (value2 % 61) + ( (value1*value2) % hash);
        return resultHash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Area other = (Area) o;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.name.equals(other.name)) {
            return false;
        }
        if (this.creatures != other.creatures && (this.creatures == null || !this.creatures.equals(other.creatures))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Area: " + "id=" + id + ", name=" + name;
    }            
}
