package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserItem {

    private String fullName;
    private String email;
    private String role;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserItem)) return false;

        UserItem that = (UserItem) o;

        return (fullName != null && fullName.equals(that.fullName))
                && (email != null && email.equals(that.email))
                && (role != null && role.equals(that.role))
                && (password != null && password.equals(that.password));
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "UserItem[fullName=%s, email=%s, role=%s, password=%s]",
                fullName, email, role, password
        );
    }
}