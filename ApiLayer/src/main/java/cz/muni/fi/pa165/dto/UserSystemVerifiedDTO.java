package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Karel Vaculik
 */
public class UserSystemVerifiedDTO {

    @NotNull
    private Long userId;

    @NotNull
    @Size(min=3, max=64)
    private String loginName;

    @NotNull
    private Boolean admin;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "UserSystemVerifiedDTO{" +
                "userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", admin=" + admin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSystemVerifiedDTO)) return false;

        UserSystemVerifiedDTO that = (UserSystemVerifiedDTO) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return !(getLoginName() != null ? !getLoginName().equals(that.getLoginName()) : that.getLoginName() != null);
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getLoginName() != null ? getLoginName().hashCode() : 0);
        return result;
    }
}
