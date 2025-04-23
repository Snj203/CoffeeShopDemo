package kg.devcats.coffee_shop.service.impl.api;

import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.entity.postgres.State;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequest;
import kg.devcats.coffee_shop.repository.postgres.CityRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.StateRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.SupplierRepositoryJPA;
import kg.devcats.coffee_shop.service.api.SupplierService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepositoryJPA supplierRepositoryJPA;
    private final CityRepositoryJPA cityRepositoryJPA;
    private final StateRepositoryJPA stateRepositoryJPA;

    public SupplierServiceImpl(SupplierRepositoryJPA supplierRepositoryJPA, CityRepositoryJPA cityRepositoryJPA, StateRepositoryJPA stateRepositoryJPA) {
        this.supplierRepositoryJPA = supplierRepositoryJPA;
        this.cityRepositoryJPA = cityRepositoryJPA;
        this.stateRepositoryJPA = stateRepositoryJPA;
    }


    @Override
    public boolean save(SupplierRequest request) {
        Supplier supplier = new Supplier();
        return saveSupplier(supplier, request);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepositoryJPA.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return  supplierRepositoryJPA.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Optional<Supplier> supplier = supplierRepositoryJPA.findById(id);
            if(supplier.isPresent()) {
                supplierRepositoryJPA.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, SupplierRequest request) {
        Optional<Supplier> optionalSupplier= supplierRepositoryJPA.findById(id);
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

        Optional<City> optionalCity = cityRepositoryJPA.findById(request.city());
        Optional<State> optionalState = stateRepositoryJPA.findById(request.state());
        if(!optionalCity.isPresent() || !optionalState.isPresent()) {
            return false;
        }
        supplier.setCity(optionalCity.get());
        supplier.setState(optionalState.get());
        supplierRepositoryJPA.save(supplier);
        return true;
    }
}
