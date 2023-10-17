package com.laamrani.customerservice;

import com.laamrani.customerservice.entities.Customer;
import com.laamrani.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	// CommandLineRunner pour l'initialisation des données de test au démarrage de l'application
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return args -> {
			// Ajout de clients
			customerRepository.save(new Customer(null,"Oumaima","ouma@gmail.com"));
			customerRepository.save(new Customer(null,"Nouhaila","nouha@gmail.com"));
			customerRepository.save(new Customer(null,"Saas","saad@gmail.com"));
			// Affichage des clients dans la console
			customerRepository.findAll().forEach(customer -> {
				System.out.println(customer.toString());
			});
		};
	}

}
