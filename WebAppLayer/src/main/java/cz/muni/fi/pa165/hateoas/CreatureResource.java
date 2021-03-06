package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.enums.CreatureType;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Karel Vaculik
 */

@Relation(value = "creature", collectionRelation = "creatures")
@JsonPropertyOrder(value = {"id", "name", "height", "weight", "type", "description"})
public class CreatureResource extends ResourceSupport {

    @JsonProperty("id")
    private long id;

    private String name;
    private Integer height;
    private Integer weight;
    private CreatureType type;
    private String description;

    public CreatureResource(CreatureDTO creatureDTO) {
        this.id = creatureDTO.getId();
        this.name = creatureDTO.getName();
        this.height = creatureDTO.getHeight();
        this.weight = creatureDTO.getWeight();
        this.type = creatureDTO.getType();
        this.description = creatureDTO.getDescription();
    }

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public CreatureType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
