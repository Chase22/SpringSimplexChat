package org.chase.chat.simplexchat.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    public UserEntity findFirstByName(String name);
}
