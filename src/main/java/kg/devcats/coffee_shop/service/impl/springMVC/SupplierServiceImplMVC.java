package kg.devcats.coffee_shop.service.impl.springMVC;

import kg.devcats.coffee_shop.entity.postgres.City;
import kg.devcats.coffee_shop.entity.postgres.State;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CityRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.StateRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.SupplierRepositoryJPA;
import kg.devcats.coffee_shop.service.mvc.SupplierServiceMVC;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SupplierServiceImplMVC implements SupplierServiceMVC {
    private final SupplierRepositoryJPA supplierService;
    private final CityRepositoryJPA cityService;
    private final StateRepositoryJPA stateService;

    public SupplierServiceImplMVC(SupplierRepositoryJPA supplierService, CityRepositoryJPA cityService, StateRepositoryJPA stateService) {
        this.supplierService = supplierService;
        this.cityService = cityService;
        this.stateService = stateService;
    }


    @Override
    public boolean update(Long id, SupplierRequestMVC request) {
        Optional<City> city = cityService.findById(request.getCityName());
        Optional<State> state = stateService.findById(request.getStateName());
        Optional<Supplier> optionalSupplier = supplierService.findById(id);
        if(!checkCityState(city, state) || !optionalSupplier.isPresent()) {
            return false;
        }

        Supplier supplier = optionalSupplier.get();
        supplier.setName(request.getName());
        supplier.setStreet(request.getStreet());
        supplier.setZip(request.getZip());
        supplier.setState(state.get());
        supplier.setCity(city.get());

        supplierService.save(supplier);
        return true;
    }

    @Override
    public boolean save(SupplierRequestMVC request) {
        Optional<City> city = cityService.findById(request.getCityName());
        Optional<State> state = stateService.findById(request.getStateName());
        if(!checkCityState(city, state)){
            return false;
        }

        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setStreet(request.getStreet());
        supplier.setZip(request.getZip());
        supplier.setState(state.get());
        supplier.setCity(city.get());

        supplierService.save(supplier);
        return true;
    }

    private boolean checkCityState(Optional<City> city , Optional<State> state) {
        if(!city.isPresent() || !state.isPresent()) {
            return false;
        }
        if(!city.get().getState().equals(state.get())) {
            return false;
        }
        return true;
    }
}
