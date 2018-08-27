package userservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import userservice.model.Role;
import userservice.model.UserEntity;

import java.util.Optional;

/**
 * repository for UserEntity Entity
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity getUserByLogin(String login);
}