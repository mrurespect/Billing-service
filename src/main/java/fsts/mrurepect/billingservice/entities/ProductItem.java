package fsts.mrurepect.billingservice.entities;

import fsts.mrurepect.billingservice.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder  @Getter @Setter
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    @ManyToOne
    private Bill bill;
    private int quantity;
    private Long productId;
    private double discount;
    @Transient
    private Product product ;
}
