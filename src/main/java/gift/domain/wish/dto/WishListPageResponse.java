package gift.domain.wish.dto;

import org.springframework.data.domain.Page;

public record WishListPageResponse(Page<WishResponse> wishResponses) {
}
