package kg.devcats.coffee_shop.service.impl.api;

import kg.devcats.coffee_shop.entity.postgres.Coffee;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.coffee.request.CoffeeRequest;
import kg.devcats.coffee_shop.repository.postgres.CoffeeRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.SupplierRepositoryJPA;
import kg.devcats.coffee_shop.repository.storage_to_file_system.StorageService;
import kg.devcats.coffee_shop.service.api.CoffeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public class CoffeeServiceImpl implements CoffeeService {

    private static final Logger log = LoggerFactory.getLogger(CoffeeServiceImpl.class);
    private final CoffeeRepositoryJPA coffeeRepositoryJPA;
    private final SupplierRepositoryJPA supplierRepositoryJPA;
    private final StorageService storageService;

    @Value("${spring.storage.default-photo-name}")
    private String defaultPhotoName;

    public CoffeeServiceImpl(CoffeeRepositoryJPA coffeeRepositoryJPA, SupplierRepositoryJPA supplierRepositoryJPA, StorageService storageService) {
        this.coffeeRepositoryJPA = coffeeRepositoryJPA;
        this.supplierRepositoryJPA = supplierRepositoryJPA;
        this.storageService = storageService;
    }

    @Override
    public boolean save(CoffeeRequest request, MultipartFile photo) {
        Optional<Supplier> optionalSupplier;
        optionalSupplier = supplierRepositoryJPA.findById(request.supplierId());
        if(!optionalSupplier.isPresent()) {
            return false;
        }

        Coffee coffee = new Coffee();
        String fileName = storageService.store(photo).toString();
        coffee.setName(request.name());
        coffee.setPrice(request.price());
        coffee.setSold(0);
        coffee.setTotal(0);
        coffee.setSupplier(optionalSupplier.get());
        coffee.setPhoto(fileName);

        coffeeRepositoryJPA.save(coffee);
        return true;
    }

    @Override
    public Optional<Coffee> findById(String id) {
        return coffeeRepositoryJPA.findById(id);
    }

    @Override
    public List<Coffee> findAll() {
        return coffeeRepositoryJPA.findAll();
    }

    @Override
    public boolean deleteByIdCoffee(String id) {
        try {
            Optional<Coffee> coffee = coffeeRepositoryJPA.findById(id);
            if(coffee.isPresent()) {
                coffeeRepositoryJPA.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(CoffeeRequest request, MultipartFile photo) {
        Optional<Coffee> optionalCoffee= coffeeRepositoryJPA.findById(request.name());
        Optional<Supplier> optionalSupplier = supplierRepositoryJPA.findById(request.supplierId());
        if(!optionalCoffee.isPresent() || !optionalSupplier.isPresent()) {
            return false;
        }

        Coffee coffee = optionalCoffee.get();
        coffee.setPrice(request.price());
        coffee.setSupplier(optionalSupplier.get());


        if(coffee.getPhoto().equals(defaultPhotoName)) {
            coffee.setPhoto(storageService.store(photo).toString());
        } else if(!storageService.update(coffee.getPhoto(), photo)) {
            storageService.delete(coffee.getPhoto());
            coffee.setPhoto(storageService.store(photo).toString());
        }


        coffeeRepositoryJPA.save(coffee);
        return true;
    }





}
