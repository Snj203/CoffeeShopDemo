package kg.devcats.coffee_shop.service.api;

import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequest;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    boolean save(SupplierRequest request);
    Optional<Supplier> findById(Long id);
    List<Supplier> findAll();
    boolean deleteById(Long id);
    boolean update(Long id, SupplierRequest request);
}
