package com.mindhub.homebanking.controllers;



import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repository.AccountRepository;
import com.mindhub.homebanking.repository.TransactionRepository;
import com.mindhub.homebanking.repository.clientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@Transactional
public class transactionController {
    @Autowired
    private clientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @PostMapping("/api/clients/current/transaction")
    public ResponseEntity<?> response(
            Authentication authentication, @RequestParam Double amount, @RequestParam String description, @RequestParam String numberAccountOrigin, @RequestParam String numberAccountDestini
    ) {     Client client = clientRepository.findByEmail(authentication.getName());
            Account accountOrigin = accountRepository.findByNumber(numberAccountOrigin);
            Account accountDestini = accountRepository.findByNumber(numberAccountDestini);


        if (amount.toString().isEmpty() || description.isEmpty() || numberAccountOrigin.isEmpty() || numberAccountDestini.isEmpty()) {
            return new ResponseEntity<>("El monto, la descripcion, numero de Origen, o numero de destino estan vacios", HttpStatus.FORBIDDEN);
        }
        if (numberAccountOrigin.equals(numberAccountDestini)) {
            return new ResponseEntity<>("El numero de Cuenta de origen es igual al numero de Cuenta de Destino", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("El monto a Transferir es menor a su saldo o su cuenta no tiene saldo", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().stream().map(account -> account.getNumber()).collect(Collectors.toList()).contains(numberAccountOrigin)){
            return new ResponseEntity<>("La cuenta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        if (accountOrigin == null){return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if (accountDestini == null){return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        Transaction transactionOrigin = new Transaction(TransactionType.Debit, -amount, description, LocalDateTime.now(), accountOrigin);
        transactionRepository.save(transactionOrigin);
        accountOrigin.addTransaction(transactionOrigin);
        accountOrigin.setBalance(accountOrigin.getBalance() - amount);
        accountRepository.save(accountOrigin);

        Transaction transactionDestini = new Transaction(TransactionType.Credit, amount, description, LocalDateTime.now(), accountDestini);
        transactionRepository.save(transactionDestini);
        accountDestini.addTransaction(transactionDestini);
        accountDestini.setBalance(accountDestini.getBalance() + amount);
        accountRepository.save(accountDestini);

        return new ResponseEntity<>("Se realizó la transferencia de manera exitosa", HttpStatus.ACCEPTED);
    }
}
