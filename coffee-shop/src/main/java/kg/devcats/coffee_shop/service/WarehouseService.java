package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.Warehouse;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierAddWarehouseRequest;
import kg.devcats.coffee_shop.payload.warehouse.request.WarehouseInitRequest;
import kg.devcats.coffee_shop.payload.warehouse.request.WarehouseRequest;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    boolean createWithNoRelations(WarehouseInitRequest request);
    boolean save(WarehouseRequest request);
    Optional<Warehouse> findById(Long id);
    List<Warehouse> findAll();
    boolean deleteById(Long id);
    boolean update(Long id, WarehouseRequest request);
}
