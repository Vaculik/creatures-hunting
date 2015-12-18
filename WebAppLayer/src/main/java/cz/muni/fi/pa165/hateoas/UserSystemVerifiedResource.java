package cz.muni.fi.pa165.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.muni.fi.pa165.dto.UserSystemVerifiedDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

/**
 * @author Karel Vaculik
 */

@Relation(value = "user-verified", collectionRelation = "users-verified")
@JsonPropertyOrder(value = {"userId", "loginName", "admin"})
public class UserSystemVerifiedResource extends ResourceSupport {

    @JsonProperty("userId")
    private Long userId;

    private String loginName;

    private boolean admin;

    public UserSystemVerifiedResource(UserSystemVerifiedDTO userVerifiedDTO) {
        this.userId = userVerifiedDTO.getUserId();
        this.loginName = userVerifiedDTO.getUserLoginName();
        this.admin = userVerifiedDTO.getAdmin();
    }

    public Long getUserId() {
        return userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public boolean isAdmin() {
        return admin;
    }
}
