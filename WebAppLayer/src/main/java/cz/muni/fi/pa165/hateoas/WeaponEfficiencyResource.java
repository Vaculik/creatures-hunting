package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.dto.WeaponEfficiencyDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Karel Vaculik
 */

@Relation(value = "weapon-efficiency", collectionRelation = "weapon-efficiencies")
@JsonPropertyOrder(value = {"id", "efficiency", "creatureDTO", "weaponDTO"})
public class WeaponEfficiencyResource extends ResourceSupport {

    @JsonProperty("id")
    private long id;

    private Integer efficiency;
    private CreatureDTO creatureDTO;
    private WeaponDTO weaponDTO;

    public WeaponEfficiencyResource(WeaponEfficiencyDTO weaponEfficiencyDTO) {
        this.id = weaponEfficiencyDTO.getId();
        this.efficiency = weaponEfficiencyDTO.getEfficiency();
        this.creatureDTO = weaponEfficiencyDTO.getCreature();
        this.weaponDTO = weaponEfficiencyDTO.getWeapon();
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public CreatureDTO getCreatureDTO() {
        return creatureDTO;
    }

    public WeaponDTO getWeaponDTO() {
        return weaponDTO;
    }
}
