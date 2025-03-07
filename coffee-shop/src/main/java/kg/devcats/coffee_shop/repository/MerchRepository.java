package kg.devcats.coffee_shop.repository;

import kg.devcats.coffee_shop.entity.Merch;
import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequest;
import kg.devcats.coffee_shop.repository.jpa.MerchServiceJPA;
import kg.devcats.coffee_shop.repository.jpa.SupplierServiceJPA;
import kg.devcats.coffee_shop.service.MerchService;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MerchRepository implements MerchService {
    private final MerchServiceJPA merchService;
    private final SupplierServiceJPA supplierService;


    public MerchRepository(MerchServiceJPA merchService, SupplierServiceJPA supplierService) {
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
    public boolean update(Long id, MerchRequest request) {
        Optional<Supplier> optionalSupplier = supplierService.findById(request.supplierId());
        Optional<Merch> optionalMerch = merchService.findById(id);
        if (!optionalMerch.isPresent() || !optionalSupplier.isPresent()) {
            return false;
        }

        Merch merch = optionalMerch.get();
        merch.setName(request.itemName());
        merch.setQuantity(0);
        merch.setTime(Timestamp.valueOf(LocalDateTime.now()));
        merch.setSupplier(optionalSupplier.get());

        merchService.save(merch);

        return true;
    }
}
