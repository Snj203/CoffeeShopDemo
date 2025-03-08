package kg.devcats.coffee_shop.service.api;

import kg.devcats.coffee_shop.entity.Merch;
import kg.devcats.coffee_shop.payload.merch.request.MerchRequest;
import kg.devcats.coffee_shop.payload.merch.request.MerchUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface MerchService {
    boolean save(MerchRequest request);
    Optional<Merch> findById(Long id);
    List<Merch> findAll();
    boolean deleteById(Long id);
    boolean update(Long id, MerchUpdateRequest request);
}
