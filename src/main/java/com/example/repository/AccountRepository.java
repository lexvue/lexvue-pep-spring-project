package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByUsername(String username);

    Account findAccountByUsernameAndPassword(String username, String password);

    Account findAccountByAccountId(Integer accountId);



}
