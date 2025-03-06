package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.Warehouse;
import kg.devcats.coffee_shop.payload.warehouse.response.WarehouseResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarehouseMapper {

    public WarehouseResponse toResponse(Warehouse warehouse) {
        return null;
    }

    public List<WarehouseResponse> toResponses(List<Warehouse> warehouseList){
        List<WarehouseResponse> responses = new ArrayList<>();
        for (Warehouse warehouse : warehouseList) {
            responses.add(toResponse(warehouse));
        }
        return responses;
    }
}
