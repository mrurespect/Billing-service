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
			//Collection<Product> products = productRestClient.getProducts().getContent();
			//Collection<Customer> customers = customerRestClient.getCustomers().getContent();
			List<Product> products = productRestClient.getProducts().getContent().stream().toList();
			List<Customer> customers = customerRestClient.getCustomers().getContent().stream().toList();

			Random random =new Random();

			for (int i = 0; i < 20; i++) {
				Bill bill =Bill.builder()
								.customerId(customers.get(random.nextInt(customers.size())).getId())
								.billingDate(new Date())
								.build();
				Bill savedBill = billRepository.save(bill);

				if(Math.random() > 0.5){
					products.forEach(prod -> {
						ProductItem productItem = new ProductItem();
						productItem.setPrice(prod.getPrice());
						productItem.setQuantity(1+new Random().nextInt(10));
						productItem.setBill(savedBill);
						productItem.setDiscount(Math.random());
						productItem.setProductId(prod.getId());
						productItem.setProduct(prod);
						productItemRepository.save(productItem);
					});
				}
			}
		};
	}
}

