package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}



	@Bean
	public CommandLineRunner initData(clientRepository clientRepository,AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("1234"));
			Client client2 = new Client("Matias", "Milich", "Mat210705@gmail.com",passwordEncoder.encode("1654"));
			Client client3 = new Client("Rogelio", "Morel", "rogelio@minhub.com",passwordEncoder.encode("1947"));
			Account cuenta1 = new Account("VIN001", LocalDateTime.now(), 5000D);
			Account cuenta2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500D);
			Account cuenta3 = new Account("VIN003", LocalDateTime.now(), 2000D);
			Account cuenta4 = new Account("VIN004", LocalDateTime.now(), 15000D);
			Transaction transaction1 = new Transaction(TransactionType.Credit, -1000, "Compra", LocalDateTime.now(), cuenta1);
			Transaction transaction2 = new Transaction(TransactionType.Debit, -2500, "Pago de Servicios", LocalDateTime.now(), cuenta1);
			Transaction transaction3 = new Transaction(TransactionType.Debit, 1500, "Cobro Sueldo", LocalDateTime.now(), cuenta2);
			Transaction transaction4 = new Transaction(TransactionType.Debit, -5000, "Pago de Impuestos", LocalDateTime.now(), cuenta3);
			List<Integer> cuotas;
			Loan prestamo1 = new Loan("Hipotecario", 500000, cuotas = List.of(12, 24, 48, 60));
			Loan prestamo2 = new Loan("Personal", 100000, cuotas = List.of(6, 12, 24));
			Loan prestamo3 = new Loan("Automotriz", 300000, cuotas = List.of(6, 12, 24, 36));
			Loan prestamo4 = new Loan("Personal", 150000, cuotas = List.of(24, 36));
			ClientLoan prestamoHipotecario = new ClientLoan(400000D, 60, client1, prestamo1, "VIN001");
			ClientLoan prestamoPersonal = new ClientLoan(100000D, 12, client1, prestamo2, "VIN002");
			ClientLoan prestamoAutomotriz = new ClientLoan(300000D, 24, client2, prestamo3, "VIN003");
			Card tarjeta1 = new Card(client1.getFirstName()+ " " +client1.getLastName(), CardType.DEBIT, CardColor.GOLD, "1234"+"-"+"5678"+"-"+"9123"+"-"+"4567", 124, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1);
			Card tarjeta2 = new Card(client1.getFirstName()+ " " + client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "9874"+"-"+"5632"+"-"+"1234"+"-"+"4569", 342, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1);
			Card tarjeta3 = new Card(client2.getFirstName()+ " " + client2.getLastName(), CardType.DEBIT, CardColor.SILVER, "9874518974445699", 3791, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client2);


			client1.addAccount(cuenta1);
			client1.addAccount(cuenta2);
			client2.addAccount(cuenta3);
			client3.addAccount(cuenta4);

			cuenta1.addTransaction(transaction1);
			cuenta1.addTransaction(transaction2);
			cuenta2.addTransaction(transaction3);
			cuenta3.addTransaction(transaction4);

			client1.addClientLoan(prestamoHipotecario);
			prestamo1.addClientLoan(prestamoHipotecario);
			client1.addClientLoan(prestamoPersonal);
			prestamo2.addClientLoan(prestamoPersonal);
			client2.addClientLoan(prestamoAutomotriz);
			prestamo3.addClientLoan(prestamoAutomotriz);




			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
			accountRepository.save(cuenta3);
			accountRepository.save(cuenta4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			loanRepository.save(prestamo1);
			loanRepository.save(prestamo2);
			loanRepository.save(prestamo3);

			clientLoanRepository.save(prestamoHipotecario);
			clientLoanRepository.save(prestamoPersonal);
			clientLoanRepository.save(prestamoAutomotriz);

			cardRepository.save(tarjeta1);
			cardRepository.save(tarjeta2);
			cardRepository.save(tarjeta3);



		};
	}
}
