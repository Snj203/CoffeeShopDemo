package kg.devcats.coffee_shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.List;


@Entity
@Table(name = "city", uniqueConstraints =
        {@UniqueConstraint(name = "uniquesNames", columnNames = {"city_name"})})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "city_name", unique = true)
    @JsonProperty("name")
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonProperty("state_id")
    private State state;


    public City() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
