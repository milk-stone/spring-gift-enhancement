package gift.domain.wish;

import gift.domain.member.Member;
import gift.domain.product.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Wish {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    protected Wish() {
    }

    public Wish(Member member, Product product, int quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return member.getId();
    }

    public Long getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public void update(int quantity){
        this.quantity = quantity;
    }
}
