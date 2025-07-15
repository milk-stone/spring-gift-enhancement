package gift.domain.wish.service;

import gift.domain.auth.service.AuthService;
import gift.domain.member.Member;
import gift.domain.wish.Wish;
import gift.domain.wish.dto.WishRequest;
import gift.domain.wish.dto.WishResponse;
import gift.domain.wish.dto.WishUpdateRequest;
import gift.domain.wish.repository.WishRepository;
import gift.global.exception.TokenExpiredException;
import gift.global.exception.WishNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository, AuthService authService) {
        this.wishRepository = wishRepository;
    }

    public void createWish(WishRequest wishRequest, Member member) {
        Wish wish = new Wish(member.getId(), wishRequest.productId(), wishRequest.quantity());
        int affectedRows = wishRepository.save(wish);
        if (affectedRows == 0) {
            throw new RuntimeException("WishService : createWish() failed - 500 Internal Server Error");
        }
    }

    public void updateWish(Long id, WishUpdateRequest req, Member member) {
        Wish wish = wishRepository.getWish(id, member.getId()).orElseThrow(() -> new WishNotFoundException(id + "가 존재하지 않습니다."));
        wish.update(req.quantity());
        if (!wishRepository.update(wish)) {
            throw new RuntimeException("WishService : updateWish() failed (quantity != 0) - 500 Internal Server Error");
        }
    }

    public void deleteWish(Long id, Member member) {
        Wish wish = wishRepository.getWish(id, member.getId()).orElseThrow(() -> new WishNotFoundException(id + "가 존재하지 않습니다."));
        if (!wishRepository.delete(wish.getId(), member.getId())) {
            throw new RuntimeException("WishService : deleteWish() failed - 500 Internal Server Error");
        }
    }

    public List<WishResponse> getWishes(Member member) {
        return wishRepository.getAllWishes(member.getId()).stream()
                .map(WishResponse::from)
                .toList();
    }
}
