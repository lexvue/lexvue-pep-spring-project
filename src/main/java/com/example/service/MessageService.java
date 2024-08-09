package com.example.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        return this.messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return this.messageRepository.findMessageByMessageId(messageId);
    }

    public int deleteMessageById(Integer messageId) {
        return this.messageRepository.deleteMessageByMessageId(messageId);
    }

    public int updateMessageById(Integer messageId, String messageText) {
        return this.messageRepository.updateMessageTextByMessageId(messageId, messageText);
    }
    
    public List<Message> getAllMessagesFromUser(Integer postedBy) {
        return this.messageRepository.findMessagesByPostedBy(postedBy);
    }

}
