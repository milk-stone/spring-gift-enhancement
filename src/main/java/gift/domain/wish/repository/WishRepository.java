package gift.domain.wish.repository;

import gift.domain.wish.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query(value = "SELECT * FROM wish WHERE member_id = :memberId", nativeQuery = true)
    List<Wish> findAllByMember(Long memberId);
}
