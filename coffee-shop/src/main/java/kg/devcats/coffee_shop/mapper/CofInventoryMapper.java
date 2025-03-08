package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.CofInventory;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryUpdateRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.response.CofInventoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CofInventoryMapper {

    public CofInventoryResponse toResponse(CofInventory cofInventory) {
         CofInventoryResponse cofInventoryResponse = new CofInventoryResponse();
         cofInventoryResponse.setId(cofInventory.getId());
         cofInventoryResponse.setQuantity(cofInventory.getQuantity());
         cofInventoryResponse.setTime(cofInventory.getTime());
         cofInventoryResponse.setCoffeeName(cofInventory.getCoffee().getName());
         cofInventoryResponse.setSupplierId(cofInventory.getSupplier().getId());
         cofInventoryResponse.setWarehouseId(cofInventory.getWarehouseId());

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
