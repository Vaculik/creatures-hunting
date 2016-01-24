package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Karel Vaculik
 */
public class UserSystemLoginDTO {

    @NotNull
    @Size(min=3, max=64)
    private String loginName;

    @NotNull
    @Size(min=3, max=64)
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSystemLoginDTO)) return false;

        UserSystemLoginDTO that = (UserSystemLoginDTO) o;

        if (getLoginName() != null ? !getLoginName().equals(that.getLoginName()) : that.getLoginName() != null)
            return false;
        return !(getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null);

    }

    @Override
    public int hashCode() {
        int result = getLoginName() != null ? getLoginName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSystemLoginDTO{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
