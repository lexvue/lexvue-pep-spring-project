package com.example.service;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class AccountService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }
    

    public Account registerNewAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findAccountById(Integer accountId) {
        return accountRepository.findAccountByAccountId(accountId);
    }

    public Account findAccountByUsernameAndPassword(String username, String password) {
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }

    public boolean doesAccountExistById(Integer accountId) {
        if (accountRepository.findAccountByAccountId(accountId) == null)
            return false;
        else
            return true;
        
    }

    public boolean doesAccountExistByUsername(String username) {
        if (accountRepository.findByUsername(username) == null)
            return false;
        else 
            return true;
        
    }
}
