package cz.muni.fi.pa165.dto;

/**
 * @author Karel Vaculik
 */

public class UserSystemChangePasswordDTO {

    private Long userId;

    private String originalPassword;

    private String newPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSystemChangePasswordDTO)) return false;

        UserSystemChangePasswordDTO that = (UserSystemChangePasswordDTO) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        if (getOriginalPassword() != null ? !getOriginalPassword().equals(that.getOriginalPassword()) : that.getOriginalPassword() != null)
            return false;
        return !(getNewPassword() != null ? !getNewPassword().equals(that.getNewPassword()) : that.getNewPassword() != null);

    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getOriginalPassword() != null ? getOriginalPassword().hashCode() : 0);
        result = 31 * result + (getNewPassword() != null ? getNewPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSystemChangePasswordDTO{" +
                "userId=" + userId +
                ", originalPassword='" + originalPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
