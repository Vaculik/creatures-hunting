package cz.muni.fi.pa165.entity;

import javax.persistence.*;

/**
 * Created by vaculik on 23.10.15.
 */
@Entity
public class WeaponEfficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer efficiency;

    @ManyToOne(optional = false)
    private Creature creature;

    @ManyToOne(optional = false)
    private Weapon weapon;

    public Long getId() {
        return id;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public Creature getCreature() {
        return creature;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 23 * hash + ((efficiency != null) ? efficiency.hashCode() : 0);
        hash = 11 * hash + ((creature != null) ? creature.hashCode() : 0);
        hash = 29 * hash + ((weapon != null) ? weapon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WeaponEfficiency)) {
            return false;
        }
        WeaponEfficiency other = (WeaponEfficiency) obj;

        if (other.getEfficiency() == null) {
            if (this.efficiency != null) {
                return false;
            }
        } else if (!other.getEfficiency().equals(this.efficiency)) {
            return false;
        }

        if (other.getCreature() == null) {
            if (this.creature != null) {
                return false;
            }
        } else if (!other.getCreature().equals(this.creature)) {
            return false;
        }

        if (other.getWeapon() == null) {
            if (this.weapon != null) {
                return false;
            }
        } else if ( !other.getWeapon().equals(this.weapon)) {
            return false;
        }

        return true;
    }
}
