package services.customerService;

import DTO.customerDTO.responseDTO.FurnitureOfOrderResponseDTO;

import java.util.List;

public interface IFurnitureOfOrderService {
    List<FurnitureOfOrderResponseDTO> getProductOfOrder(Long orderID);
    Long totalPriceOfOrder(Long orderId);

}
