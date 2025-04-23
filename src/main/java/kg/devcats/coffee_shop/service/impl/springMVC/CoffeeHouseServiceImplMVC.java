package kg.devcats.coffee_shop.service.impl.springMVC;

import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CityRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.CoffeeHouseRepositoryJPA;
import kg.devcats.coffee_shop.service.mvc.CoffeeHouseServiceMVC;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CoffeeHouseServiceImplMVC implements CoffeeHouseServiceMVC {
    private final CityRepositoryJPA cityService;
    private final CoffeeHouseRepositoryJPA cofHouseService;

    public CoffeeHouseServiceImplMVC(CityRepositoryJPA cityService, CoffeeHouseRepositoryJPA cofHouseService) {
        this.cityService = cityService;
        this.cofHouseService = cofHouseService;
    }


    @Override
    public boolean save(CoffeeHouseRequestMVC request) {
        Optional<City> city = cityService.findById(request.getCity());
        if (!city.isPresent() ||
                request.getSoldCoffee() + request.getSoldMerch() != request.getTotalSold()) {
            return false;
        }
        CoffeeHouse coffeeHouse = new CoffeeHouse();
        coffeeHouse.setCity(city.get());
        coffeeHouse.setSoldCoffee(request.getSoldCoffee());
        coffeeHouse.setSoldMerch(request.getSoldMerch());
        coffeeHouse.setTotalSold(request.getTotalSold());
        coffeeHouse.setPrefixState(city.get().getState().getPrefix());

        cofHouseService.save(coffeeHouse);
        return true;
    }

    @Override
    public boolean update(Long id, CoffeeHouseRequestMVC request) {
        Optional<City> city = cityService.findById(request.getCity());
        Optional<CoffeeHouse> optionalCoffeeHouse = cofHouseService.findById(id);
        if (!city.isPresent() || !optionalCoffeeHouse.isPresent() ||
                request.getSoldCoffee() + request.getSoldMerch() != request.getTotalSold()) {
            return false;
        }
        CoffeeHouse coffeeHouse = optionalCoffeeHouse.get();
        coffeeHouse.setCity(city.get());
        coffeeHouse.setSoldCoffee(request.getSoldCoffee());
        coffeeHouse.setSoldMerch(request.getSoldMerch());
        coffeeHouse.setTotalSold(request.getTotalSold());
        coffeeHouse.setPrefixState(city.get().getState().getPrefix());

        cofHouseService.save(coffeeHouse);
        return true;
    }

}
