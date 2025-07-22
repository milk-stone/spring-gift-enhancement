package gift.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.regex.Pattern;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;
    @NotBlank
    private String name;
    private Integer quantity;
    @ManyToOne
    private Product product;

    private static final Pattern ALLOWED_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&\\/_]*$");


    protected Option() {
    }

    public Option(String name, Integer quantity, Product product) {
        validateName(name);
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name cannot be longer than 50 characters");
        }
        if (!ALLOWED_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("옵션 이름에 허용되지 않는 특수문자가 포함되어 있습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
