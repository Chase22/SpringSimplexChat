package org.chase.chat.simplexchat.message;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
}
