package kg.devcats.coffee_shop.service.impl.api;

import kg.devcats.coffee_shop.entity.postgres.Merch;
import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequest;
import kg.devcats.coffee_shop.payload.merch.request.MerchUpdateRequest;
import kg.devcats.coffee_shop.repository.postgres.MerchRepositoryJPA;
import kg.devcats.coffee_shop.repository.postgres.SupplierRepositoryJPA;
import kg.devcats.coffee_shop.service.api.MerchService;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MerchServiceImpl implements MerchService {
    private final MerchRepositoryJPA merchService;
    private final SupplierRepositoryJPA supplierService;


    public MerchServiceImpl(MerchRepositoryJPA merchService, SupplierRepositoryJPA supplierService) {
        this.merchService = merchService;
        this.supplierService = supplierService;
    }


    @Override
    public boolean save(MerchRequest request) {

        Optional<Supplier> optionalSupplier = supplierService.findById(request.supplierId());
        if (!optionalSupplier.isPresent()) {
            return false;
        }

        Merch merch = new Merch();
        merch.setName(request.itemName());
        merch.setQuantity(0);
        merch.setTime(Timestamp.valueOf(LocalDateTime.now()));
        merch.setSupplier(optionalSupplier.get());

        merchService.save(merch);

        return true;
    }

    @Override
    public Optional<Merch> findById(Long id) {
        return merchService.findById(id);
    }

    @Override
    public List<Merch> findAll() {
        return merchService.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Optional<Merch> merch = merchService.findById(id);
            if(merch.isPresent()) {
                merchService.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Long id, MerchUpdateRequest request) {
        Optional<Merch> optionalMerch = merchService.findById(id);
        if (!optionalMerch.isPresent()) {
            return false;
        }

        Merch merch = optionalMerch.get();
        merch.setName(request.itemName());
        merch.setQuantity(0);
        merch.setTime(Timestamp.valueOf(LocalDateTime.now()));
        merchService.save(merch);

        return true;
    }
}
