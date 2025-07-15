package gift.domain.wish.controller;

import gift.domain.annotation.LoginMember;
import gift.domain.member.Member;
import gift.domain.wish.dto.WishListResponse;
import gift.domain.wish.dto.WishRequest;
import gift.domain.wish.dto.WishResponse;
import gift.domain.wish.dto.WishUpdateRequest;
import gift.domain.wish.service.WishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<Void> createWish(
            @Valid @RequestBody WishRequest wishRequest,
            @LoginMember Member member
    ) {
        wishService.createWish(wishRequest, member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWish(
            @PathVariable Long id,
            @Valid @RequestBody WishUpdateRequest wishUpdateRequest,
            @LoginMember Member member
    ) {
        wishService.updateWish(id, wishUpdateRequest, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WishResponse> deleteWish(
            @PathVariable Long id,
            @LoginMember Member member
    ) {
        wishService.deleteWish(id, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<WishListResponse> getWishes(
            @LoginMember Member member
    ) {
        return new ResponseEntity<>(new WishListResponse(wishService.getWishes(member)), HttpStatus.OK);
    }
}
