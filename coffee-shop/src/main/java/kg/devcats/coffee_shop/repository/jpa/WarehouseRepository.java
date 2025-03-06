package kg.devcats.coffee_shop.repository.jpa;

import kg.devcats.coffee_shop.entity.Coffee;
import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.entity.Warehouse;
import kg.devcats.coffee_shop.payload.warehouse.request.WarehouseInitRequest;
import kg.devcats.coffee_shop.payload.warehouse.request.WarehouseRequest;
import kg.devcats.coffee_shop.repository.jpa.interfaces.CoffeeServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.interfaces.SupplierServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.interfaces.WarehouseServiceJPA;
import kg.devcats.coffee_shop.service.WarehouseService;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class WarehouseRepository implements WarehouseService {
    private final WarehouseServiceJPA warehouseService;
    private final SupplierServiceJPA supplierService;
    private final CoffeeServiceJPA coffeeService;
    private final WarehouseServiceJPA warehouseServiceJPA;

    public WarehouseRepository(WarehouseServiceJPA warehouseService, SupplierServiceJPA supplierService, CoffeeServiceJPA coffeeService, WarehouseServiceJPA warehouseServiceJPA) {
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.coffeeService = coffeeService;
        this.warehouseServiceJPA = warehouseServiceJPA;
    }

    @Override
    public boolean createWithNoRelations(WarehouseInitRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.name());
        warehouse.setSupplierList(Collections.emptyList());
        warehouse.setCoffeeList(Collections.emptyList());
        warehouse.setTime(Timestamp.valueOf(LocalDateTime.now()));
        warehouse.setQuantity(0);
        warehouseService.save(warehouse);
        return true;
    }

    @Override
    public boolean save(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.name());
        warehouse.setQuantity(0);

        List<Coffee> coffees = new ArrayList<>();
        List<Supplier> suppliers = new ArrayList<>();
        for(Long id : request.coffeeList()){
            coffees.add(coffeeService.getById(id));
        }
        for(Long id : request.supplierList()){
            suppliers.add(supplierService.getById(id));
        }
        if(coffees.isEmpty() && suppliers.isEmpty()){
            return false;
        }
        warehouse.setCoffeeList(coffees);
        warehouse.setSupplierList(suppliers);

        warehouse.setTime(Timestamp.valueOf(LocalDateTime.now()));
        warehouseService.save(warehouse);

        return true;
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseService.findById(id);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseService.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Optional<Warehouse> warehouse = warehouseService.findById(id);
            if(warehouse.isPresent()) {
                warehouseService.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, WarehouseRequest request) {
        Optional<Warehouse> optionalWarehouse = warehouseService.findById(id);
        if(!optionalWarehouse.isPresent()) {
            return false;
        }

        Warehouse warehouse = optionalWarehouse.get();
        warehouse.setName(request.name());

        List<Coffee> coffees = new ArrayList<>();
        for(Long idCoffee : request.coffeeList()){
            coffees.add(coffeeService.getById(idCoffee));
        }

        List<Supplier> suppliers = new ArrayList<>();
        for(Long idSupplier : request.supplierList()){
            suppliers.add(supplierService.getById(idSupplier));
        }

        if(coffees.isEmpty() && suppliers.isEmpty()){
            return false;
        }
        warehouse.setCoffeeList(coffees);
        warehouse.setSupplierList(suppliers);

        warehouseServiceJPA.save(warehouse);
        return true;
    }
}
