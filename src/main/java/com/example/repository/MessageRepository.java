package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findMessageByMessageId(Integer messageId);
    int deleteMessageByMessageId(Integer messageId);

    @Modifying
    @Query("UPDATE Message SET messageText = :messageText WHERE messageId = :messageId")
    int updateMessageTextByMessageId(@Param("messageId") Integer messageId, @Param("messageText") String messageText);

    List<Message> findMessagesByPostedBy(Integer postedBy);
}
