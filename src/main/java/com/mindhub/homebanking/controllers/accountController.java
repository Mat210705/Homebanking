package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.clientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;


import java.util.List;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api")

public class accountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private clientRepository repo;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccount() {

        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }
    @RequestMapping("/accounts/{id}")

    public AccountDTO getAccountDTO(@PathVariable long id){

        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> getAllClient(Authentication authentication) {

            Client client = repo.findByEmail(authentication.getName());

        if (client.getAccounts().size() > 2){
            return new ResponseEntity<>("Already Have 3 accounts", HttpStatus.FORBIDDEN);
        }

        Random rnd=new Random();
        int aleatorio=rnd.nextInt(99999999);
        Account newAccount = new Account("VIN" + aleatorio, LocalDateTime.now(),0.0);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}


