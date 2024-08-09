package com.example.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.web.bind.annotation.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register") 
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (doesAccountPassRequirements(account)) {
            String username = account.getUsername();
            if (!accountService.doesAccountExistByUsername(username)) {
                account = accountService.registerNewAccount(account);
                return ResponseEntity.status(200).body(account);
            } else {
                return ResponseEntity.status(409).body(null);
            }
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    private boolean doesAccountPassRequirements(Account account) {
        if (account != null && !account.getUsername().equals("")) {
            if (account.getPassword() != null && account.getPassword().length() >= 4) {
                return true;
            }
        }
        return false;
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {     
        Account checkedAcc = getVerifiedLogin(account);
        return checkedAcc != null ? ResponseEntity.status(200).body(checkedAcc) : ResponseEntity.status(401).body(null);
    }

    private Account getVerifiedLogin(Account account) {
        if (account != null) {
            return accountService.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        } else {
            return null;
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message verifiedMessage = doesMessageMeetRequirements(message.getMessageText(), message.getPostedBy()) ? messageService.createMessage(message) : null;
        return verifiedMessage != null ? ResponseEntity.status(200).body(verifiedMessage) : ResponseEntity.status(400).body(null);
    }

    private boolean doesMessageMeetRequirements(String messageText, Integer messagePostedBy) {
        return doesMessagePostedByExist(messagePostedBy) ? isMessageTextValid(messageText) : false;
    }

    private boolean isMessageTextValid(String messageText) {
        return (!messageText.isBlank() && messageText.length() <= 255);
    }

    private boolean doesMessagePostedByExist(Integer postedBy) {
        return accountService.doesAccountExistById(postedBy);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        int updatedRows = messageService.deleteMessageById(messageId);
        return (updatedRows > 0) ? ResponseEntity.status(200).body(updatedRows) : ResponseEntity.status(200).body(null);
    }
    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message newMessage) {
        int updatedRows = 0;
        String messageText = newMessage.getMessageText();
        if (isMessageTextValid(messageText)) {
            updatedRows = messageService.updateMessageById(messageId, messageText);
        } 
        return (updatedRows > 0) ? ResponseEntity.status(200).body(updatedRows) : ResponseEntity.status(400).body(null);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable Integer accountId) {
        Account account = accountService.findAccountById(accountId);
        if (account != null) { 
            return ResponseEntity.status(200).body(messageService.getAllMessagesFromUser(account.getAccountId()));
        } 

        return ResponseEntity.status(200).body(null);

    }

}
