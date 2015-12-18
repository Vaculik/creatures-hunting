package cz.muni.fi.pa165.dto;

/**
 * @author Karel Vaculik
 */
public class UserSystemVerifiedDTO {

    private Long userId;

    private String userLoginName;

    private Boolean admin;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
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
                ", userLoginName='" + userLoginName + '\'' +
                ", admin=" + admin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSystemVerifiedDTO)) return false;

        UserSystemVerifiedDTO that = (UserSystemVerifiedDTO) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return !(getUserLoginName() != null ? !getUserLoginName().equals(that.getUserLoginName()) : that.getUserLoginName() != null);
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getUserLoginName() != null ? getUserLoginName().hashCode() : 0);
        return result;
    }
}
