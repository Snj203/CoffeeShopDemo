package kg.devcats.coffee_shop.repository;

import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.entity.CofInventory;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryReplenishRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryUpdateRequest;
import kg.devcats.coffee_shop.repository.jpa.CoffeeServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.SupplierServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.CofInventoryServiceJPA;
import kg.devcats.coffee_shop.service.CofInventoryService;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CofInventoryRepository implements CofInventoryService {
    private final CofInventoryServiceJPA cofInventoryService;
    private final SupplierServiceJPA supplierService;
    private final CoffeeServiceJPA coffeeService;

    public CofInventoryRepository(CofInventoryServiceJPA cofInventoryService, SupplierServiceJPA supplierService, CoffeeServiceJPA coffeeService) {
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
    public boolean update(Long id, CofInventoryUpdateRequest request) {
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
    public boolean save(CofInventoryUpdateRequest request) {
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
