package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Karel Vaculik
 */
public class AreaAddCreatureDTO {

    @NotNull
    private Long areaId;

    @NotNull
    private String creatureName;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getCreatureName() {
        return creatureName;
    }

    public void setCreatureName(String id) {
        this.creatureName = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AreaAddCreatureDTO)) return false;

        AreaAddCreatureDTO that = (AreaAddCreatureDTO) o;

        if (getAreaId() != null ? !getAreaId().equals(that.getAreaId()) : that.getAreaId() != null) return false;
        return !(getCreatureName() != null ? !getCreatureName().equals(that.getCreatureName()) : that.getCreatureName() != null);

    }

    @Override
    public int hashCode() {
        int result = getAreaId() != null ? getAreaId().hashCode() : 0;
        result = 31 * result + (getCreatureName() != null ? getCreatureName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AreaAddCreatureDTO{" +
                "areaId=" + areaId +
                ", creatureName=" + creatureName +
                '}';
    }
}
