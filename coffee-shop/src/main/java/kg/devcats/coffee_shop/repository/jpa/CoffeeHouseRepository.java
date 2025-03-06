package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequest;
import kg.devcats.coffee_shop.repository.jpa.interfaces.CoffeeHouseServiceJPA;
import kg.devcats.coffee_shop.service.CoffeeHouseService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoffeeHouseRepository implements CoffeeHouseService {
    private final CoffeeHouseServiceJPA coffeeHouseServiceJPA;

    public CoffeeHouseRepository(CoffeeHouseServiceJPA coffeeHouseServiceJPA) {
        this.coffeeHouseServiceJPA = coffeeHouseServiceJPA;
    }


    @Override
    public boolean save(CoffeeHouseRequest request) {
        CoffeeHouse coffee = new CoffeeHouse();
        coffee.setCity(request.city());
        coffee.setSoldCoffee(0);
        coffee.setSoldMerch(0);
        coffee.setTotalSold(0);
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
        CoffeeHouse coffee = optionalCoffeeHouse.get();
        coffee.setCity(request.city());
        coffeeHouseServiceJPA.save(coffee);
        return true;
    }
}
