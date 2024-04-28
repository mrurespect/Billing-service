package fsts.mrurepect.billingservice.clients;

import fsts.mrurepect.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE") // name of the service to call (customer-service)
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    Customer getCustomerById(@PathVariable Long id);
    @GetMapping(path = "/customers")
    PagedModel<Customer> getCustomers();
}
