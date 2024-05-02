package fsts.mrurepect.billingservice.web;

import fsts.mrurepect.billingservice.dto.BillResponceDto;
import fsts.mrurepect.billingservice.entities.Bill;
import fsts.mrurepect.billingservice.repository.BillRepository;
import fsts.mrurepect.billingservice.repository.ProductItemRepository;
import fsts.mrurepect.billingservice.clients.CustomerRestClient;
import fsts.mrurepect.billingservice.clients.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        Bill bill = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found"));
        assert bill != null;
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(productRestClient.getProductById(pi.getProductId()));
        });
        return bill;
    }
    @GetMapping(path = "/fullBill")
    public List<BillResponceDto> fullBill() {
        List<BillResponceDto> billsList =new ArrayList<>();
        List<Bill> bills =billRepository.findAll();
        for (Bill bill : bills) {
            BillResponceDto responce = new BillResponceDto();
            responce.setId(bill.getId());
            responce.setBillDate(bill.getBillingDate());
            responce.setCustomerName(customerRestClient.getCustomerById(bill.getCustomerId()).getName());
            billsList.add(responce);
        }
        return billsList;
    }
}
