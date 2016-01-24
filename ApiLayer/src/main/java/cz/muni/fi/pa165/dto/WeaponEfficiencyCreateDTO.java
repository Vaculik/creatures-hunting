package cz.muni.fi.pa165.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object for creating new WeaponEfficiency entity.
 *
 * @author Karel Vaculik
 */
public class WeaponEfficiencyCreateDTO {

    @NotNull
    @Min(0)
    private Integer efficiency;

    @NotNull
    private Long creatureId;

    @NotNull
    private Long weaponId;

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Long getCreatureId() {
        return creatureId;
    }

    public void setCreatureId(Long creatureId) {
        this.creatureId = creatureId;
    }

    public Long getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(Long weaponId) {
        this.weaponId = weaponId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeaponEfficiencyCreateDTO)) return false;

        WeaponEfficiencyCreateDTO that = (WeaponEfficiencyCreateDTO) o;

        if (getEfficiency() != null ? !getEfficiency().equals(that.getEfficiency()) : that.getEfficiency() != null)
            return false;
        if (getCreatureId() != null ? !getCreatureId().equals(that.getCreatureId()) : that.getCreatureId() != null)
            return false;
        return !(getWeaponId() != null ? !getWeaponId().equals(that.getWeaponId()) : that.getWeaponId() != null);

    }

    @Override
    public int hashCode() {
        int result = getEfficiency() != null ? getEfficiency().hashCode() : 0;
        result = 31 * result + (getCreatureId() != null ? getCreatureId().hashCode() : 0);
        result = 31 * result + (getWeaponId() != null ? getWeaponId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeaponEfficiencyCreateDTO{" +
                "efficiency=" + efficiency +
                ", creatureId=" + creatureId +
                ", weaponId=" + weaponId +
                '}';
    }
}
