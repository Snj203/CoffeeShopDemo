package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.Supplier;
import kg.devcats.coffee_shop.payload.supplier.response.SupplierResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {


    public SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setZip(supplier.getZip());
        response.setStreet(supplier.getStreet());
        response.setCity(supplier.getCity().getName());
        response.setState(supplier.getState().getName());

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
