package kg.devcats.coffee_shop.repository;

import kg.devcats.coffee_shop.entity.*;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequest;
import kg.devcats.coffee_shop.repository.jpa.*;
import kg.devcats.coffee_shop.service.SupplierService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierRepository implements SupplierService {
    private final SupplierServiceJPA supplierServiceJPA;
    private final CityServiceJPA cityServiceJPA;
    private final StateServiceJPA stateServiceJPA;

    public SupplierRepository(SupplierServiceJPA supplierServiceJPA, CityServiceJPA cityServiceJPA, StateServiceJPA stateServiceJPA) {
        this.supplierServiceJPA = supplierServiceJPA;
        this.cityServiceJPA = cityServiceJPA;
        this.stateServiceJPA = stateServiceJPA;
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
        supplierServiceJPA.save(supplier);
        return true;
    }
}
