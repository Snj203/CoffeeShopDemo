package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierAddWarehouseRequest;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierInitRequest;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequest;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    boolean createWithNoRelations(SupplierInitRequest request);
    boolean save(SupplierRequest request);
    Optional<Supplier> findById(Long id);
    List<Supplier> findAll();
    boolean deleteById(Long id);
    boolean update(Long id, SupplierRequest request);
    boolean addWarehouse(SupplierAddWarehouseRequest request);
    boolean removeWarehouse(SupplierAddWarehouseRequest request);
}
