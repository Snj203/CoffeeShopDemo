package kg.devcats.coffee_shop.mapper;

import kg.devcats.coffee_shop.entity.Merch;
import kg.devcats.coffee_shop.payload.merch.response.MerchResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MerchMapper {

    public MerchResponse toResponse(Merch merch) {
        MerchResponse merchResponse = new MerchResponse();
        merchResponse.setName(merch.getName());
        merchResponse.setItemId(merch.getItemId());
        merchResponse.setQuantity(merch.getQuantity());
        merchResponse.setTime(merch.getTime());
        merchResponse.setSupplier(merch.getSupplier().getId());

        return merchResponse;
    }

    public List<MerchResponse> toResponses(List<Merch> merchList){
        List<MerchResponse> responses = new ArrayList<>();
        for (Merch merch : merchList) {
            responses.add(toResponse(merch));
        }
        return responses;
    }
}
