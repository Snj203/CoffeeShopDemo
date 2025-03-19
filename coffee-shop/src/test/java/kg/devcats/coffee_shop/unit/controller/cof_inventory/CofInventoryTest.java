package kg.devcats.coffee_shop.unit.controller.cof_inventory;

import kg.devcats.coffee_shop.controller.api.CofInventoryControllerAPI;
import kg.devcats.coffee_shop.entity.postgres.CofInventory;
import kg.devcats.coffee_shop.mapper.CofInventoryMapper;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryReplenishRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.request.CofInventoryRequest;
import kg.devcats.coffee_shop.payload.cof_inventory.response.CofInventoryResponse;
import kg.devcats.coffee_shop.service.CofInventoryService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CofInventoryTest {
    private CofInventoryResponse cofInventoryResponse;
    private CofInventoryRequest cofInventoryRequest;
    
    @Mock
    private CofInventoryService cofInventoryService;

    @Mock
    private CofInventoryMapper mapper;

    @InjectMocks
    private CofInventoryControllerAPI cofInventoryControllerAPI;

    @BeforeEach
    void setUp() {
        cofInventoryResponse = new CofInventoryResponse(1L,1L,"cofName",1L,
                1, Timestamp.valueOf(LocalDateTime.now()));
        cofInventoryRequest = new CofInventoryRequest(1L,"cofName",1L,1);
    }

    @Test
    void testGetCofInventories() {
        
        List<CofInventory> cofInventories = new ArrayList<>();
        cofInventories.add(new CofInventory());
        when(cofInventoryService.findAll()).thenReturn(cofInventories);
        when(mapper.toResponses(any())).thenReturn(List.of(cofInventoryResponse));

        
        ResponseEntity<List<CofInventoryResponse>> response = cofInventoryControllerAPI.getCofInventories(null);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetCofInventoriesById() {
        
        CofInventory cofInventory = new CofInventory();
        when(cofInventoryService.findById(1L)).thenReturn(Optional.of(cofInventory));
        when(mapper.toResponse(any())).thenReturn(cofInventoryResponse);

        
        ResponseEntity<List<CofInventoryResponse>> response = cofInventoryControllerAPI.getCofInventories(1L);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetCofInventoriesNotFound() {
        
        when(cofInventoryService.findById(1L)).thenReturn(Optional.empty());

        
        ResponseEntity<List<CofInventoryResponse>> response = cofInventoryControllerAPI.getCofInventories(1L);

        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCreateCofInventory() {
        
        CofInventoryRequest request = cofInventoryRequest;
        when(cofInventoryService.save(request)).thenReturn(true);


        ResponseEntity<String> response = cofInventoryControllerAPI.createCofInventory(request);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("CofInventory created", response.getBody());
    }

    @Test
    void testCreateCofInventoryFailed() {

        CofInventoryRequest request = cofInventoryRequest;
        when(cofInventoryService.save(request)).thenReturn(false);

        
        ResponseEntity<String> response = cofInventoryControllerAPI.createCofInventory(request);

        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to create CofInventory", response.getBody());
    }

    @Test
    void testReplenishCofInventory() {
        
        CofInventoryReplenishRequest request = new CofInventoryReplenishRequest();
        when(cofInventoryService.findById(1L)).thenReturn(Optional.of(new CofInventory()));

        
        ResponseEntity<String> response = cofInventoryControllerAPI.replenishCofInventory(request, 1L);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CofInventory was updated successfully.", response.getBody());
    }

    @Test
    void testReplenishCofInventoryNotFound() {
        
        CofInventoryReplenishRequest request = new CofInventoryReplenishRequest();
        when(cofInventoryService.findById(1L)).thenReturn(Optional.empty());

        
        ResponseEntity<String> response = cofInventoryControllerAPI.replenishCofInventory(request, 1L);

        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cannot find CofInventory", response.getBody());
    }
}
