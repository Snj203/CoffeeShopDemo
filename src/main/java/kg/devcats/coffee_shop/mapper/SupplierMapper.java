package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.postgres.Supplier;
import kg.devcats.coffee_shop.payload.supplier.response.SupplierResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {


    public SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse(
            supplier.getId(),
            supplier.getName(),
            supplier.getStreet(),
            supplier.getCity().getName(),
            supplier.getZip(),
            supplier.getState().getName()
        );
        return response;
    }

    public List<SupplierResponse> toResponses(List<Supplier> supplierList){
        List<SupplierResponse> responses = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            responses.add(toResponse(supplier));
        }
        return responses;
    }
}
