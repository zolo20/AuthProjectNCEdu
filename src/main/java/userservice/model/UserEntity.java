package userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users", schema = "public")
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="user_id", nullable = false, unique = true)
    private Long userID;

    @Basic
    @Column(nullable = false, unique = true)
    private String login;

    @Basic
    @Column(nullable = false)
    private String password;

    @Basic
    @Column(nullable = false)
    private String name;

    @Basic
    @Column(nullable = false)
    private String surname;

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
