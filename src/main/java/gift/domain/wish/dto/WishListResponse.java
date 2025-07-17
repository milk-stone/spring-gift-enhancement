package gift.domain.wish.dto;

import org.springframework.data.domain.Page;

public record WishListResponse(Page<WishResponse> wishResponses) {
}
