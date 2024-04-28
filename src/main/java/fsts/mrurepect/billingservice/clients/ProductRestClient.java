package fsts.mrurepect.billingservice.clients;

import fsts.mrurepect.billingservice.model.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping(path = "/products/{id}")
    @CircuitBreaker(name = "inventory-service", fallbackMethod = "getProductByIdFallback")
    Product getProductById(@PathVariable Long id);

    @GetMapping(path = "/products")
    @CircuitBreaker(name = "inventory-service", fallbackMethod = "getProductsFallback")
    PagedModel<Product> getProducts();

    default Product getProductByIdFallback(Long id, Exception e) {
        Product product = new Product();
        product.setId(id);
        product.setName("Not Available");
        product.setPrice(0.0);
        return product;
    }

    default PagedModel<Product> getProductsFallback(Exception e) {
        return PagedModel.empty();
    }
}
