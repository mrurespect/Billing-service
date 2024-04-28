package fsts.mrurepect.billingservice.clients;

import fsts.mrurepect.billingservice.model.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE") // name of the service to call (customer-service)
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    @CircuitBreaker(name = "customer-service", fallbackMethod = "getCustomerByIdFallback")
    Customer getCustomerById(@PathVariable Long id);
    @GetMapping(path = "/customers")
    @CircuitBreaker(name = "customer-service", fallbackMethod = "getCustomersFallback")
    PagedModel<Customer> getCustomers();

    default Customer getCustomerByIdFallback(Long id, Exception e) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Not Available");
        customer.setEmail("Not Available");
        return customer;
    }

    default PagedModel<Customer> getCustomersFallback(Exception e) {
        return PagedModel.empty();
    }
}
