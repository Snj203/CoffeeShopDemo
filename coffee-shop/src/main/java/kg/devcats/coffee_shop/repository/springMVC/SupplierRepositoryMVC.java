package kg.devcats.coffee_shop.repository.springMVC;

import kg.devcats.coffee_shop.entity.City;
import kg.devcats.coffee_shop.entity.State;
import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.payload.supplier.request.SupplierRequestMVC;
import kg.devcats.coffee_shop.repository.jpa.CityServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.StateServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.SupplierServiceJPA;
import kg.devcats.coffee_shop.service.mvc.SupplierServiceMVC;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SupplierRepositoryMVC implements SupplierServiceMVC {
    private final SupplierServiceJPA supplierService;
    private final CityServiceJPA cityService;
    private final StateServiceJPA stateService;

    public SupplierRepositoryMVC(SupplierServiceJPA supplierService, CityServiceJPA cityService, StateServiceJPA stateService) {
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
