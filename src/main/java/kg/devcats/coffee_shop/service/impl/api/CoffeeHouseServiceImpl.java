package kg.devcats.coffee_shop.service.impl.api;

import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequest;
import kg.devcats.coffee_shop.repository.postgres.CityRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.CoffeeHouseRepositoryJPA;
import kg.devcats.coffee_shop.service.api.CoffeeHouseService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoffeeHouseServiceImpl implements CoffeeHouseService {
    private final CoffeeHouseRepositoryJPA coffeeHouseRepositoryJPA;
    private final CityRepositoryJPA cityRepositoryJPA;

    public CoffeeHouseServiceImpl(CoffeeHouseRepositoryJPA coffeeHouseRepositoryJPA, CityRepositoryJPA cityRepositoryJPA) {
        this.coffeeHouseRepositoryJPA = coffeeHouseRepositoryJPA;
        this.cityRepositoryJPA = cityRepositoryJPA;
    }


    @Override
    public boolean save(CoffeeHouseRequest request) {
        CoffeeHouse coffee = new CoffeeHouse();

        Optional<City> optionalCity = cityRepositoryJPA.findById(request.city());
        if (!optionalCity.isPresent()) {
            return false;
        }

        City city = optionalCity.get();
        coffee.setCity(city);
        coffee.setSoldCoffee(0);
        coffee.setSoldMerch(0);
        coffee.setTotalSold(0);
        coffee.setPrefixState(city.getState().getPrefix());
        coffeeHouseRepositoryJPA.save(coffee);

        return true;
    }

    @Override
    public Optional<CoffeeHouse> findById(Long id) {
        return coffeeHouseRepositoryJPA.findById(id);
    }

    @Override
    public List<CoffeeHouse> findAll() {
        return coffeeHouseRepositoryJPA.findAll();
    }

    @Override
    public boolean deleteByIdCoffee(Long id) {
        try {
            Optional<CoffeeHouse> coffee = coffeeHouseRepositoryJPA.findById(id);
            if(coffee.isPresent()) {
                coffeeHouseRepositoryJPA.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, CoffeeHouseRequest request) {
        Optional<CoffeeHouse> optionalCoffeeHouse= coffeeHouseRepositoryJPA.findById(id);
        if(!optionalCoffeeHouse.isPresent()) {
            return false;
        }
        Optional<City> optionalCity = cityRepositoryJPA.findById(request.city());
        if (!optionalCity.isPresent()) {
            return false;
        }
        CoffeeHouse coffee = optionalCoffeeHouse.get();
        coffee.setCity(optionalCity.get());
        coffee.setPrefixState(coffee.getCity().getState().getPrefix());
        coffeeHouseRepositoryJPA.save(coffee);
        return true;
    }
}
