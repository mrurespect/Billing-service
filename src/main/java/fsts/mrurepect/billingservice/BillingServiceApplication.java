package fsts.mrurepect.billingservice;

import fsts.mrurepect.billingservice.entities.Bill;
import fsts.mrurepect.billingservice.entities.ProductItem;
import fsts.mrurepect.billingservice.model.Customer;
import fsts.mrurepect.billingservice.model.Product;
import fsts.mrurepect.billingservice.repository.BillRepository;
import fsts.mrurepect.billingservice.repository.ProductItemRepository;
import fsts.mrurepect.billingservice.service.CustomerRestClient;
import fsts.mrurepect.billingservice.service.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							ProductRestClient productRestClient) {
		return args -> {
			Collection<Product> products = productRestClient.pageProducts().getContent();
			Bill bill = new Bill();
			bill.setBillingDate(new Date());

			Long id =1L;
			// on s assure que le client existe avec cet id
			Customer customer =customerRestClient.getCustomerById(id);
			if (customer == null) throw new RuntimeException("Customer not found");
			System.out.println(customer);
			bill.setCustomerId(customer.getId());
			bill.setCustomer(customer);
			Bill savedBill = billRepository.save(bill);

			products.forEach(prod -> {
				ProductItem productItem = new ProductItem();
				productItem.setPrice(prod.getPrice());
				productItem.setQuantity(1+new Random().nextInt(10));
				productItem.setBill(savedBill);
				productItem.setDiscount(Math.random());
				productItem.setProductId(prod.getId());
				productItem.setProduct(prod);

				System.out.println(productItemRepository.save(productItem));
			});




		};
	}
}

