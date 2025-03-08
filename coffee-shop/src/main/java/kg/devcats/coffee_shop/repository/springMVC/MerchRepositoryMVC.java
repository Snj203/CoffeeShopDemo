package kg.devcats.coffee_shop.repository.springMVC;

import kg.devcats.coffee_shop.entity.CofInventory;
import kg.devcats.coffee_shop.entity.Merch;
import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.payload.merch.request.MerchReplenishRequest;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequestMVC;
import kg.devcats.coffee_shop.repository.jpa.MerchServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.SupplierServiceJPA;
import kg.devcats.coffee_shop.service.mvc.MerchServiceMVC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class MerchRepositoryMVC implements MerchServiceMVC {
    private final MerchServiceJPA merchService;
    private final SupplierServiceJPA supplierService;

    public MerchRepositoryMVC(MerchServiceJPA merchService, SupplierServiceJPA supplierService) {
        this.merchService = merchService;
        this.supplierService = supplierService;
    }

    @Override
    public boolean update(Long id, MerchRequestMVC request) {
        Optional<Merch> optionalMerch = merchService.findById(id);
        Optional<Supplier> optionalSupplier = supplierService.findById(request.getSupplierId());
        if(!optionalSupplier.isPresent() || !optionalMerch.isPresent()) {
            return false;
        }

        Merch merch = optionalMerch.get();
        merch.setName(request.getName());
        merch.setQuantity(request.getQuantity());
        merch.setSupplier(optionalSupplier.get());
        merch.setTime(Timestamp.valueOf(LocalDateTime.now()));

        merchService.save(merch);
        return true;
    }

    @Override
    public boolean save(MerchRequestMVC request) {
        Optional<Supplier> optionalSupplier;
        optionalSupplier = supplierService.findById(request.getSupplierId());
        if(!optionalSupplier.isPresent()) {
            return false;
        }

        Merch merch = new Merch();
        merch.setName(request.getName());
        merch.setQuantity(request.getQuantity());
        merch.setSupplier(optionalSupplier.get());
        merch.setTime(Timestamp.valueOf(LocalDateTime.now()));

        merchService.save(merch);
        return true;

    }

    @Override
    public boolean replenish(Long id, MerchReplenishRequest request) {
        Optional<Merch> optionalMerch = merchService.findById(id);
        if(!optionalMerch.isPresent()) {
            return false;
        }

        Merch merch = optionalMerch.get();
        merch.setQuantity(request.getQuantity() + merch.getQuantity());
        merch.setTime(Timestamp.valueOf(LocalDateTime.now()));

        merchService.save(merch);
        return true;
    }
}
