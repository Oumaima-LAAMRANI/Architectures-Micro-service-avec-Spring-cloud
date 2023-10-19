package com.laamrani.billingservice;

import com.laamrani.billingservice.entities.Bill;
import com.laamrani.billingservice.entities.ProductItem;
import com.laamrani.billingservice.feign.CustomerRestClient;
import com.laamrani.billingservice.feign.ProductItemRestClient;
import com.laamrani.billingservice.model.Customer;
import com.laamrani.billingservice.model.Product;
import com.laamrani.billingservice.repository.BillRepository;
import com.laamrani.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@EnableFeignClients
@SpringBootApplication
public class BillingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient){
		return args -> {
			Customer customer= customerRestClient.getCustomerById(1L);
			Bill bill = billRepository.save(new Bill(null,new Date(), null, customer.getId(), customer));
			PagedModel<Product> productPagedModel= productItemRestClient.pageProducts();
			productPagedModel.forEach(p->{
				ProductItem productItem=new ProductItem();
				productItem.setPrice(p.getPrice());
				productItem.setQuantity(1+new Random().nextInt(100));
				productItem.setProductID(p.getId());
				productItem.setBill(bill);
				productItemRepository.save(productItem);
			});
		};
	}
}
