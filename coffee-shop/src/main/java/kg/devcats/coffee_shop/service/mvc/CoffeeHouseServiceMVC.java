package kg.devcats.coffee_shop.service.mvc;

import kg.devcats.coffee_shop.payload.coffeehouse.request.CoffeeHouseRequestMVC;

public interface CoffeeHouseServiceMVC {
    boolean save(CoffeeHouseRequestMVC request);
    boolean update(Long id,CoffeeHouseRequestMVC request);
}
