package cz.muni.fi.pa165.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object of the WeaponEfficiency entity.
 *
 * @author Karel Vaculik
 */
public class WeaponEfficiencyDTO {

    private Long id;

    @NotNull
    @Min(0)
    private Integer efficiency;

    @NotNull
    private CreatureDTO creature;

    @NotNull
    private WeaponDTO weapon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public CreatureDTO getCreature() {
        return creature;
    }

    public void setCreature(CreatureDTO creature) {
        this.creature = creature;
    }

    public WeaponDTO getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponDTO weapon) {
        this.weapon = weapon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeaponEfficiencyDTO)) return false;

        WeaponEfficiencyDTO that = (WeaponEfficiencyDTO) o;

        if (getEfficiency() != null ? !getEfficiency().equals(that.getEfficiency()) : that.getEfficiency() != null)
            return false;
        if (getCreature() != null ? !getCreature().equals(that.getCreature()) : that.getCreature() != null)
            return false;
        return !(getWeapon() != null ? !getWeapon().equals(that.getWeapon()) : that.getWeapon() != null);

    }

    @Override
    public int hashCode() {
        int result = getEfficiency() != null ? getEfficiency().hashCode() : 0;
        result = 29 * result + (getCreature() != null ? getCreature().hashCode() : 0);
        result = 29 * result + (getWeapon() != null ? getWeapon().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeaponEfficiencyDTO{" +
                "id=" + id +
                ", efficiency=" + efficiency +
                ", creature=" + creature +
                ", weapon=" + weapon +
                '}';
    }
}