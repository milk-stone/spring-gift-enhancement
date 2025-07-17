package gift.domain.product.dto;

import org.springframework.data.domain.Page;

public record ProductPageResponse(Page<ProductResponse> productResponseList) {
}
