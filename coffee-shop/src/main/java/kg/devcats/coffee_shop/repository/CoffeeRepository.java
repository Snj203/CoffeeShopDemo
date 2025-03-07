package kg.devcats.coffee_shop.repository;

import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.entity.Warehouse;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequest;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeUpdateRequest;
import kg.devcats.coffee_shop.repository.jpa.CoffeeServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.SupplierServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.WarehouseServiceJPA;
import kg.devcats.coffee_shop.service.CoffeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoffeeRepository implements CoffeeService {

    private static final Logger log = LoggerFactory.getLogger(CoffeeRepository.class);
    private final CoffeeServiceJPA coffeeServiceJPA;
    private final SupplierServiceJPA supplierServiceJPA;
    private final WarehouseServiceJPA warehouseServiceJPA;

    public CoffeeRepository(CoffeeServiceJPA coffeeServiceJPA, SupplierServiceJPA supplierServiceJPA, WarehouseServiceJPA warehouseServiceJPA) {
        this.coffeeServiceJPA = coffeeServiceJPA;
        this.supplierServiceJPA = supplierServiceJPA;
        this.warehouseServiceJPA = warehouseServiceJPA;
    }

    @Override
    public boolean save(CoffeeRequest request) {
        Optional<Supplier> optiopnalSupplier;
        Optional<Warehouse> optionalWarehouse;
        optiopnalSupplier = supplierServiceJPA.findById(request.supplierId());
        optionalWarehouse = warehouseServiceJPA.findById(request.warehouseId());
        if(!optiopnalSupplier.isPresent() || !optionalWarehouse.isPresent()) {
            return false;
        }

        Coffee coffee = new Coffee();

        coffee.setName(request.name());
        coffee.setPrice(request.price());
        coffee.setSold(0);
        coffee.setTotal(0);
        coffee.setSupplier(optiopnalSupplier.get());
        coffee.setWarehouse(optionalWarehouse.get());

        coffeeServiceJPA.save(coffee);
        return true;

    }

    @Override
    public Optional<Coffee> findById(Long id) {
        return coffeeServiceJPA.findById(id);
    }

    @Override
    public List<Coffee> findAll() {
        return coffeeServiceJPA.findAll();
    }

    @Override
    public boolean deleteByIdCoffee(Long id) {
        try {
            Optional<Coffee> coffee = coffeeServiceJPA.findById(id);
            if(coffee.isPresent()) {
                coffeeServiceJPA.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, CoffeeUpdateRequest request) {
        Optional<Coffee> optionalCoffee= coffeeServiceJPA.findById(id);
        if(!optionalCoffee.isPresent()) {
            return false;
        }
        Coffee coffee = optionalCoffee.get();
        coffee.setName(request.name());
        coffee.setPrice(request.price());
        coffeeServiceJPA.save(coffee);
        return true;
    }


}
