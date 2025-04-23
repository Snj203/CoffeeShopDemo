package kg.devcats.coffee_shop.service.impl.springMVC;

import kg.devcats.coffee_shop.entity.postgres.Coffee;
import kg.devcats.coffee_shop.entity.postgres.CoffeeHouse;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeBuyRequest;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequestMVC;
import kg.devcats.coffee_shop.repository.postgres.CoffeeHouseRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.CoffeeRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.SupplierRepositoryJPA;
import kg.devcats.coffee_shop.repository.storage_to_file_system.StorageService;
import kg.devcats.coffee_shop.service.mvc.CoffeeServiceMVC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CoffeeServiceImplMVC implements CoffeeServiceMVC {
    private final CoffeeRepositoryJPA coffeeService;
    private final SupplierRepositoryJPA supplierService;
    private final StorageService storageService;
    private final CoffeeHouseRepositoryJPA coffeeHouseService;

    @Value("${spring.storage.default-photo-name}")
    private String defaultPhotoName;

    public CoffeeServiceImplMVC(CoffeeRepositoryJPA coffeeService, SupplierRepositoryJPA supplierService, StorageService storageService, CoffeeHouseRepositoryJPA coffeeHouseService) {
        this.coffeeService = coffeeService;
        this.supplierService = supplierService;
        this.storageService = storageService;
        this.coffeeHouseService = coffeeHouseService;
    }

    @Override
    public boolean save(CoffeeRequestMVC request) {
        Optional<Supplier> optionalSupplier;
        optionalSupplier = supplierService.findById(request.getSupplierId());
        if(!optionalSupplier.isPresent()) {
            return false;
        }

        Coffee coffee = new Coffee();
        String fileName = storageService.store(request.getPhoto()).toString();
        coffee.setName(request.getName());
        coffee.setPrice(request.getPrice());
        coffee.setSold(0);
        coffee.setTotal(0);
        coffee.setSupplier(optionalSupplier.get());
        coffee.setPhoto(fileName);

        coffeeService.save(coffee);
        return true;

    }

    @Override
    public boolean update(CoffeeRequestMVC request) {
        Optional<Coffee> optionalCoffee= coffeeService.findById(request.getName());
        Optional<Supplier> optionalSupplier = supplierService.findById(request.getSupplierId());
        if(!optionalCoffee.isPresent() || !optionalSupplier.isPresent()) {
            return false;
        }

        Coffee coffee = optionalCoffee.get();
        coffee.setPrice(request.getPrice());
        coffee.setSupplier(optionalSupplier.get());

        if(coffee.getPhoto().equals(defaultPhotoName)) {
            coffee.setPhoto(storageService.store(request.getPhoto()).toString());
        } else if(!storageService.update(coffee.getPhoto(), request.getPhoto())) {
            storageService.delete(coffee.getPhoto());
            coffee.setPhoto(storageService.store(request.getPhoto()).toString());
        }

        coffeeService.save(coffee);
        return true;
    }

    @Override
    public boolean buy(CoffeeBuyRequest request) {
        Optional<Coffee> optionalCoffee= coffeeService.findById(request.getName());
        Optional<CoffeeHouse> optionalCoffeeHouse = coffeeHouseService.findById(request.getCoffeeHouseId());
        if(!optionalCoffee.isPresent() || !optionalCoffeeHouse.isPresent()) {
            return false;
        }
        Coffee coffee = optionalCoffee.get();
        coffee.setSold(coffee.getSold() + request.getAmount());
        coffee.setTotal(coffee.getTotal() + request.getAmount());

        CoffeeHouse coffeeHouse = optionalCoffeeHouse.get();
        coffeeHouse.setSoldCoffee(coffeeHouse.getSoldCoffee() + request.getAmount());
        coffeeHouse.setTotalSold(coffeeHouse.getTotalSold() + request.getAmount());

        coffeeService.save(coffee);
        coffeeHouseService.save(coffeeHouse);

        return true;

    }

}
