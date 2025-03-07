package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.CofInventory;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;

import java.util.List;
import java.util.Optional;

public interface CofInventoryService {
    boolean save(CofInventoryRequest request);
    Optional<CofInventory> findById(Long id);
    List<CofInventory> findAll();
    boolean deleteById(Long id);
    boolean update(Long id, CofInventoryRequest request);
}
