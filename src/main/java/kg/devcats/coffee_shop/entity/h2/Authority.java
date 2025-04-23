package kg.devcats.coffee_shop.entity.h2;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
public class Authority {
    @Id
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Id
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private User user;

    public Authority() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}