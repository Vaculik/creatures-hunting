package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import cz.muni.fi.pa165.dto.WeaponDTO;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.enums.AmmoType;

/**
 * 
 * @author Pavel Vesely <448290@mail.muni.cz>
 */
@Relation(value = "weapon", collectionRelation = "weapons")
@JsonPropertyOrder(value = {"id", "name", "type", "range", "ammoType", "description"})
public class WeaponResource extends ResourceSupport{
    @JsonProperty
    private long id;
    
    private String name;
    private WeaponType type;
    private int range;
    private AmmoType ammoType;
    private String description;
    
    public WeaponResource(WeaponDTO weaponDTO) {
        this.id = weaponDTO.getId();
        this.name = weaponDTO.getName();
        this.type = weaponDTO.getType();
        this.range = weaponDTO.getRange();
        this.ammoType = weaponDTO.getAmmoType();
        this.description = weaponDTO.getDescription();
    }
    
    public String getName() {
        return name;
    }
    public WeaponType getType() {
        return type;
    }
    public int getRange() {
        return range;
    }
    public AmmoType getAmmoType() {
        return ammoType;
    }
    public String getDescription() {
        return description;
    }
}
