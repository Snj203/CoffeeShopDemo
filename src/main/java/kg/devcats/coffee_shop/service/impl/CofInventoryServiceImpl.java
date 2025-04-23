package kg.devcats.coffee_shop.service.impl;

import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.entity.postgres.Coffee;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryModelRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryReplenishRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;
import kg.devcats.coffee_shop.repository.postgres.CofInventoryRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.CoffeeRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.SupplierRepositoryJPA;
import kg.devcats.coffee_shop.service.CofInventoryService;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CofInventoryServiceImpl implements CofInventoryService {
    private final CofInventoryRepositoryJPA cofInventoryService;
    private final SupplierRepositoryJPA supplierService;
    private final CoffeeRepositoryJPA coffeeService;

    public CofInventoryServiceImpl(CofInventoryRepositoryJPA cofInventoryService, SupplierRepositoryJPA supplierService, CoffeeRepositoryJPA coffeeService) {
        this.cofInventoryService = cofInventoryService;
        this.supplierService = supplierService;
        this.coffeeService = coffeeService;
    }

    @Override
    public boolean save(CofInventoryRequest request) {
        Optional<Supplier> supplier = supplierService.findById(request.supplierId());
        Optional<Coffee> coffee = coffeeService.findById(request.coffeeName());
        if(!coffee.isPresent() || !supplier.isPresent()) {
            return false;
        }

        CofInventory cofInventory = new CofInventory();
        cofInventory.setQuantity(request.quantity());
        cofInventory.setWarehouseId(request.warehouseId());
        cofInventory.setTime(Timestamp.valueOf(LocalDateTime.now()));
        cofInventory.setSupplier(supplier.get());
        cofInventory.setCoffee(coffee.get());

        cofInventoryService.save(cofInventory);

        return true;
    }

    @Override
    public Optional<CofInventory> findById(Long id) {
        return cofInventoryService.findById(id);
    }

    @Override
    public List<CofInventory> findAll() {
        return cofInventoryService.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<CofInventory> cofInventory = cofInventoryService.findById(id);
        if(cofInventory.isPresent()) {
            cofInventoryService.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean replenish(Long id, CofInventoryReplenishRequest request) {
        Optional<CofInventory> optionalCofInventory = cofInventoryService.findById(id);
        if(!optionalCofInventory.isPresent()) {
            return false;
        }

        CofInventory cofInventory = optionalCofInventory.get();
        cofInventory.setQuantity(request.getQuantity() + cofInventory.getQuantity());
        cofInventory.setTime(Timestamp.valueOf(LocalDateTime.now()));

        cofInventoryService.save(cofInventory);
        return true;
    }

    @Override
    public boolean update(Long id, CofInventoryModelRequest request) {
        Optional<CofInventory> optionalCofInventory = cofInventoryService.findById(id);
        Optional<Coffee> optionalCoffee = coffeeService.findById(request.getCoffeeName());
        Optional<Supplier> optionalSupplier = supplierService.findById(request.getSupplierId());
        if(!optionalCofInventory.isPresent() || !optionalCoffee.isPresent() || !optionalSupplier.isPresent()) {
            return false;
        }

        CofInventory cofInventory = optionalCofInventory.get();
        cofInventory.setCoffee(optionalCoffee.get());
        cofInventory.setSupplier(optionalSupplier.get());
        cofInventory.setWarehouseId(request.getWarehouseId());
        cofInventory.setQuantity(request.getQuantity());
        cofInventory.setTime(Timestamp.valueOf(LocalDateTime.now()));

        cofInventoryService.save(cofInventory);
        return true;
    }

    @Override
    public boolean save(CofInventoryModelRequest request) {
        Optional<Supplier> supplier = supplierService.findById(request.getSupplierId());
        Optional<Coffee> coffee = coffeeService.findById(request.getCoffeeName());
        if(!coffee.isPresent() || !supplier.isPresent()) {
            return false;
        }

        CofInventory cofInventory = new CofInventory();
        cofInventory.setQuantity(0);
        cofInventory.setWarehouseId(request.getWarehouseId());
        cofInventory.setTime(Timestamp.valueOf(LocalDateTime.now()));
        cofInventory.setSupplier(supplier.get());
        cofInventory.setCoffee(coffee.get());

        cofInventoryService.save(cofInventory);

        return true;
    }

}
