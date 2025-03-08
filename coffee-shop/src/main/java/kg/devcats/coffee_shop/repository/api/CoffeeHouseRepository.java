package kg.devcats.coffee_shop.repository.api;

import kg.devcats.coffee_shop.entity.City;
import kg.devcats.coffee_shop.entity.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequest;
import kg.devcats.coffee_shop.repository.jpa.CityServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.CoffeeHouseServiceJPA;
import kg.devcats.coffee_shop.service.api.CoffeeHouseService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoffeeHouseRepository implements CoffeeHouseService {
    private final CoffeeHouseServiceJPA coffeeHouseServiceJPA;
    private final CityServiceJPA cityServiceJPA;

    public CoffeeHouseRepository(CoffeeHouseServiceJPA coffeeHouseServiceJPA, CityServiceJPA cityServiceJPA) {
        this.coffeeHouseServiceJPA = coffeeHouseServiceJPA;
        this.cityServiceJPA = cityServiceJPA;
    }


    @Override
    public boolean save(CoffeeHouseRequest request) {
        CoffeeHouse coffee = new CoffeeHouse();

        Optional<City> optionalCity = cityServiceJPA.findById(request.city());
        if (!optionalCity.isPresent()) {
            return false;
        }

        City city = optionalCity.get();
        coffee.setCity(city);
        coffee.setSoldCoffee(0);
        coffee.setSoldMerch(0);
        coffee.setTotalSold(0);
        coffee.setPrefixState(city.getState().getPrefix());
        coffeeHouseServiceJPA.save(coffee);

        return true;
    }

    @Override
    public Optional<CoffeeHouse> findById(Long id) {
        return coffeeHouseServiceJPA.findById(id);
    }

    @Override
    public List<CoffeeHouse> findAll() {
        return coffeeHouseServiceJPA.findAll();
    }

    @Override
    public boolean deleteByIdCoffee(Long id) {
        try {
            Optional<CoffeeHouse> coffee = coffeeHouseServiceJPA.findById(id);
            if(coffee.isPresent()) {
                coffeeHouseServiceJPA.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, CoffeeHouseRequest request) {
        Optional<CoffeeHouse> optionalCoffeeHouse= coffeeHouseServiceJPA.findById(id);
        if(!optionalCoffeeHouse.isPresent()) {
            return false;
        }
        Optional<City> optionalCity = cityServiceJPA.findById(request.city());
        if (!optionalCity.isPresent()) {
            return false;
        }
        CoffeeHouse coffee = optionalCoffeeHouse.get();
        coffee.setCity(optionalCity.get());
        coffee.setPrefixState(coffee.getCity().getState().getPrefix());
        coffeeHouseServiceJPA.save(coffee);
        return true;
    }
}
