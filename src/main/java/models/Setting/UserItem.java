package models.Setting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserItem {

    private String username;
    private String email;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserItem)) return false;
        UserItem that = (UserItem) o;
        return username.equals(that.username)
                && email.equals(that.email);
    }

    @Override
    public String toString() {
        return String.format(
                "UserItem[username=%s, email=%s]",
                username, email
        );
    }
}
