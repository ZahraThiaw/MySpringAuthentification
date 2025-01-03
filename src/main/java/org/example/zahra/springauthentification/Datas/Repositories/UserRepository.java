package org.example.zahra.springauthentification.Datas.Repositories;

import org.example.zahra.springauthentification.Datas.Entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndDeletedFalse(String email);

}