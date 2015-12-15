package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 *
 * @author Martin Zboril
 */

@Relation(value = "area", collectionRelation = "areas")
@JsonPropertyOrder(value = {"id", "name", "description", "creatures"})
public class AreaResource extends ResourceSupport {
    @JsonProperty("id")
    private long id;

    private String name;
    private String description;
    List<CreatureDTO> creatures = new ArrayList<>();
    
    public AreaResource(AreaDTO areaDTO){
        this.id = areaDTO.getId();
        this.name = areaDTO.getName();
        this.description = areaDTO.getDescription();
        this.creatures = areaDTO.getCreatures();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<CreatureDTO> getCreatures() {
        return creatures;
    }
    
    
}
