package userservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import userservice.model.UserEntity;

/**
 * repository for UserEntity Entity
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity getUserByEmail(String email);
}