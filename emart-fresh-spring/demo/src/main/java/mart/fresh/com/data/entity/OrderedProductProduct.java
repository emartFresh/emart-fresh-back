package mart.fresh.com.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "ordered_product_product")
public class OrderedProductProduct {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_product_product_id")
    private int orderedProductProductId;
	
    @Column(name = "ordered_quantity")
    private int orderedQuantity;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "ordered_product_id")
    private OrderedProduct orderedProduct;
    
}