package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.WeaponType;
import java.util.Objects;

/**
 * Data transfer object of the Weapon entity.
 *
 * @author Pavel Vesely <448290@mail.muni.cz>
 */
public class WeaponDTO {
    private Long id;
    private String name;
    private WeaponType type;
    private int range;
    private AmmoType ammoType;
    private String description;
    
    public void setId(Long id) {
        this.id = id;
    }
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
        hash = 53 * hash + ((name != null) ? name.hashCode() : 0);
        hash = 59 * hash + ((type != null) ? type.hashCode() : 0);
        hash = 67 * hash + ((ammoType != null) ? ammoType.hashCode() : 0);
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
        final WeaponDTO other = (WeaponDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
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
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "WeaponDTO {" +
                "id =" + id +
                ", name='" + name + "'" +
                ", type=" + type +
                ", range=" + range +
                ", ammoType=" + ammoType +
                ", description=" + description +
                "}";
    }
}