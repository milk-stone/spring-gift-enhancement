package gift.domain.member;

import gift.domain.wish.Wish;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private RoleType role;

    @OneToMany(mappedBy = "member")
    private final List<Wish> wishList = new ArrayList<>();

    protected Member() {
    }

    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = RoleType.USER;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role.toString();
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public void update(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
