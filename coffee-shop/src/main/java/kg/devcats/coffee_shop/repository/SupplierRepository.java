package kg.devcats.coffee_shop.repository;

import kg.devcats.coffee_shop.entity.*;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierAddWarehouseRequest;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierInitRequest;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequest;
import kg.devcats.coffee_shop.repository.jpa.*;
import kg.devcats.coffee_shop.service.SupplierService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class SupplierRepository implements SupplierService {
    private final SupplierServiceJPA supplierServiceJPA;
    private final CoffeeServiceJPA coffeeServiceJPA;
    private final MerchServiceJPA merchServiceJPA;
    private final WarehouseServiceJPA warehouseServiceJPA;
    private final CityServiceJPA cityServiceJPA;
    private final StateServiceJPA stateServiceJPA;

    public SupplierRepository(SupplierServiceJPA supplierServiceJPA, CoffeeServiceJPA coffeeServiceJPA, MerchServiceJPA merchServiceJPA, WarehouseServiceJPA warehouseServiceJPA, CityServiceJPA cityServiceJPA, StateServiceJPA stateServiceJPA) {
        this.supplierServiceJPA = supplierServiceJPA;
        this.coffeeServiceJPA = coffeeServiceJPA;
        this.merchServiceJPA = merchServiceJPA;
        this.warehouseServiceJPA = warehouseServiceJPA;
        this.cityServiceJPA = cityServiceJPA;
        this.stateServiceJPA = stateServiceJPA;
    }

    @Override
    public boolean createWithNoRelations(SupplierInitRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.name());
        supplier.setStreet(request.street());
        supplier.setZip(request.zip());

        Optional<City> optionalCity = cityServiceJPA.findById(request.city());
        Optional<State> optionalState = stateServiceJPA.findById(request.state());
        if(!optionalCity.isPresent() || !optionalState.isPresent()) {
            return false;
        }
        supplier.setCity(optionalCity.get());
        supplier.setState(optionalState.get());
        supplier.setCoffeeList(Collections.emptyList());
        supplier.setMerchList(Collections.emptyList());
        supplier.setWarehouseList(Collections.emptyList());
        supplierServiceJPA.save(supplier);
        return true;
    }

    @Override
    public boolean save(SupplierRequest request) {

        Supplier supplier = new Supplier();
        return saveSupplier(supplier, request);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierServiceJPA.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return  supplierServiceJPA.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Optional<Supplier> supplier = supplierServiceJPA.findById(id);
            if(supplier.isPresent()) {
                supplierServiceJPA.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, SupplierRequest request) {
        Optional<Supplier> optionalSupplier= supplierServiceJPA.findById(id);
        if(!optionalSupplier.isPresent()) {
            return false;
        }
        Supplier supplier = optionalSupplier.get();
        return saveSupplier(supplier, request);
    }

    @Override
    public boolean addWarehouse(SupplierAddWarehouseRequest request) {
        Optional<Warehouse> optionalWarehouse = warehouseServiceJPA.findById(request.warehouseId());
        if(!optionalWarehouse.isPresent()) {
            return false;
        }
        Optional<Supplier> optionalSupplier = supplierServiceJPA.findById(request.supplierId());
        if(!optionalSupplier.isPresent()) {
            return false;
        }
        Supplier supplier = optionalSupplier.get();
        supplier.getWarehouseList().add(optionalWarehouse.get());
        Warehouse warehouse = optionalWarehouse.get();
        warehouse.getSupplierList().add(supplier);
        warehouseServiceJPA.save(warehouse);
        supplierServiceJPA.save(supplier);
        return true;
    }

    @Override
    public boolean removeWarehouse(SupplierAddWarehouseRequest request) {
        Optional<Warehouse> optionalWarehouse = warehouseServiceJPA.findById(request.warehouseId());
        if(!optionalWarehouse.isPresent()) {
            return false;
        }
        Optional<Supplier> optionalSupplier = supplierServiceJPA.findById(request.supplierId());
        if(!optionalSupplier.isPresent()) {
            return false;
        }
        Supplier supplier = optionalSupplier.get();
        supplier.getWarehouseList().remove(optionalWarehouse.get());
        Warehouse warehouse = optionalWarehouse.get();
        warehouse.getSupplierList().remove(supplier);
        warehouseServiceJPA.save(warehouse);
        supplierServiceJPA.save(supplier);
        return true;
    }

    private boolean saveSupplier(Supplier supplier, SupplierRequest request) {
        supplier.setName(request.name());
        supplier.setStreet(request.street());
        supplier.setZip(request.zip());

        Optional<City> optionalCity = cityServiceJPA.findById(request.city());
        Optional<State> optionalState = stateServiceJPA.findById(request.state());
        if(!optionalCity.isPresent() || !optionalState.isPresent()) {
            return false;
        }
        supplier.setCity(optionalCity.get());
        supplier.setState(optionalState.get());
        supplier.setWarehouseList(getWarehouseList(request.warehouseList()));
        supplier.setCoffeeList(getCoffeeList(request.coffeeList()));
        supplier.setMerchList(getMerchList(request.merchList()));
        supplierServiceJPA.save(supplier);
        return true;
    }

    private List<Coffee> getCoffeeList(List<Long> ids){
        List<Coffee> coffeeList = new ArrayList<>();
        Optional<Coffee> coffeeOptional;
        for(Long coffeeId : ids){
            coffeeOptional = coffeeServiceJPA.findById(coffeeId);
            if(coffeeOptional.isPresent()){
                coffeeList.add(coffeeOptional.get());
            }
        }
        return coffeeList;
    }

    private List<Merch> getMerchList(List<Long> ids){
        List<Merch> merchList = new ArrayList<>();
        Optional<Merch> merchOptional;
        for(Long merchId : ids){
            merchOptional = merchServiceJPA.findById(merchId);
            if(merchOptional.isPresent()){
                merchList.add(merchOptional.get());
            }
        }
        return merchList;
    }

    private List<Warehouse> getWarehouseList(List<Long> ids){
        List<Warehouse> warehouseList = new ArrayList<>();
        Optional<Warehouse> warehouseOptional;
        for(Long warehouseId : ids){
            warehouseOptional = warehouseServiceJPA.findById(warehouseId);
            if(warehouseOptional.isPresent()){
                warehouseList.add(warehouseOptional.get());
            }
        }
        return warehouseList;
    }
}
