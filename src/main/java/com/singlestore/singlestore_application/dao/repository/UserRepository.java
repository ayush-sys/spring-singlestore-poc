package com.singlestore.singlestore_application.dao.repository;

import com.singlestore.singlestore_application.dao.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
/** The User Repository */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM USER_FACT where user_state = :userstate", nativeQuery = true)
    List<UserEntity> findByUserState(@Param("userstate") String userState);

    @Query(value = "SELECT token FROM USER_FACT where username = :username", nativeQuery = true)
    String getUserAccessTokens(@Param("username") String userName);

    @Query(value = "UPDATE USER_FACT SET user_state = :userstate WHERE username = :username", nativeQuery = true)
    void updateUserState(@Param("userstate") String userState, @Param("username") String userName);

}