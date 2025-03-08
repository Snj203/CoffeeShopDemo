package kg.devcats.coffee_shop.service.mvc;

import kg.devcats.coffee_shop.payload.coffee.request.CoffeeBuyRequest;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequestMVC;


public interface CoffeeServiceMVC {
    boolean save(CoffeeRequestMVC request);
    boolean update(CoffeeRequestMVC request);
    boolean buy(CoffeeBuyRequest request);
}
