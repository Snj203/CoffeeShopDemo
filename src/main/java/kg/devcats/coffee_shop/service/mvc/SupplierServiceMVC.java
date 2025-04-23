package kg.devcats.coffee_shop.service.mvc;


import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequestMVC;

public interface SupplierServiceMVC {
    boolean update(Long id, SupplierRequestMVC request);
    boolean save( SupplierRequestMVC request);
}
