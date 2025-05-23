package kg.devcats.coffee_shop.service;

import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryModelRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryReplenishRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;

import java.util.List;
import java.util.Optional;

public interface CofInventoryService {
    boolean save(CofInventoryRequest request);
    Optional<CofInventory> findById(Long id);
    List<CofInventory> findAll();
    boolean deleteById(Long id);
    boolean replenish(Long id, CofInventoryReplenishRequest request);
    boolean update(Long id, CofInventoryModelRequest request);

    boolean save(CofInventoryModelRequest request);
}
