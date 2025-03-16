package kg.devcats.coffee_shop.service.mvc;

import kg.devcats.coffee_shop.payload.merch.request.MerchReplenishRequest;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequestMVC;

public interface MerchServiceMVC {
    boolean update(Long id, MerchRequestMVC request);
    boolean save( MerchRequestMVC request);
    boolean replenish(Long id, MerchReplenishRequest request);
}
