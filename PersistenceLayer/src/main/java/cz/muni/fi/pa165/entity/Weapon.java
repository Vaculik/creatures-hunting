package cz.muni.fi.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.enums.AmmoType;

/**
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
@Entity
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private WeaponType type;
    
    private int range;
    
    private AmmoType ammoType;
    
    private String description;
    
    public Long getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public WeaponType getType() {
        return this.type;
    }
    
    public void setType(WeaponType type) {
        this.type = type;
    }
    public int getRange() {
        return this.range;
    }
    
    public void setRange(int range) {
        this.range = range;
    }
    
    public AmmoType getAmmotype() {
        return this.ammoType;
    }
    
    public void setAmmoType(AmmoType ammoType) {
        this.ammoType = ammoType;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = 53 * hash + ((name != null)? name.hashCode() : 0);
        hash = 59 * hash + ((type != null)? type.hashCode() : 0);
        hash = 67 * hash + ((ammoType != null)? ammoType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Weapon other = (Weapon) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.range != other.range) {
            return false;
        }
        if (this.ammoType != other.ammoType) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }
}
