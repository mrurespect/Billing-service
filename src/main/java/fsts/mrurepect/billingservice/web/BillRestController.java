package fsts.mrurepect.billingservice.web;

import fsts.mrurepect.billingservice.entities.Bill;
import fsts.mrurepect.billingservice.repository.BillRepository;
import fsts.mrurepect.billingservice.repository.ProductItemRepository;
import fsts.mrurepect.billingservice.service.CustomerRestClient;
import fsts.mrurepect.billingservice.service.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {
    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductRestClient productRestClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill fullBill(@PathVariable Long id) {
        Bill bill = billRepository.findById(id).orElse(null);
        assert bill != null;
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(productRestClient.getProductById(pi.getProductId()));
        });

        return bill;
    }
}
