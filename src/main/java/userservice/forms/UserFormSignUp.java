package userservice.forms;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserFormSignUp {
    private String login;
    private String password;
    private String name;
    private String surname;
}
