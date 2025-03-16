package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.payload.cof_inventory.response.CofInventoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CofInventoryMapper {

    public CofInventoryResponse toResponse(CofInventory cofInventory) {
         CofInventoryResponse cofInventoryResponse = new CofInventoryResponse(

                 cofInventory.getId(),
                 cofInventory.getWarehouseId(),
                 cofInventory.getCoffee().getName(),
                 cofInventory.getSupplier().getId(),
                 cofInventory.getQuantity(),
                 cofInventory.getTime()
         );

         return cofInventoryResponse;
    }

    public List<CofInventoryResponse> toResponses(List<CofInventory> cofInventoryList){
        List<CofInventoryResponse> responses = new ArrayList<>();
        for (CofInventory cofInventory : cofInventoryList) {
            responses.add(toResponse(cofInventory));
        }
        return responses;
    }
}
