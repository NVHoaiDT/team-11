package services.customerService.Impl;

import DAO.customerDAO.IFurnitureDAO;
import DAO.customerDAO.impl.FurnitureDAOImpl;
import DTO.customerDTO.responseDTO.FurnitureOfOrderResponseDTO;
import business.Furniture;
import Mapper.customerConvert.FurnitureConvert;
import services.customerService.IFurnitureOfOrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnitureOfOrderServiceImpl implements IFurnitureOfOrderService {
    FurnitureConvert furnitureConvert = new FurnitureConvert();
    IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    @Override
    public List<FurnitureOfOrderResponseDTO> getProductOfOrder(Long orderID) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByOrderId(orderID);
        Map<Long, FurnitureOfOrderResponseDTO> groupedMap = new HashMap<>();
        for (Furniture furniture : furnitures) {
            Long categoryId = furniture.getCategory().getId();
            // Lấy hoặc tạo mới ProductOfOrderResponseDTO cho Category này
            FurnitureOfOrderResponseDTO dto = groupedMap.getOrDefault(categoryId, new FurnitureOfOrderResponseDTO());
            if (dto.getCategoryName() == null) {
                dto = FurnitureConvert.convertToDTO(furniture);
            }
            if (dto.getTotalPrice() == null) {
                dto.setTotalPrice(0L);
            }
            if (dto.getQuantity() == null) {
                dto.setQuantity(0L);
            }
            dto.setTotalPrice(dto.getTotalPrice() + furniture.getFurniturePrice());
            dto.setQuantity(dto.getQuantity() + 1);
            groupedMap.put(categoryId, dto);
        }
        return new ArrayList<>(groupedMap.values());
    }

    @Override
    public Long totalPriceOfOrder(Long orderID) {
        List<Furniture> furnitures = furnitureDAO.getFurnituresByOrderId(orderID);
        Long totalPrice = 0L;
        for (Furniture furniture : furnitures) {
            totalPrice += furniture.getFurniturePrice();
        }
        return totalPrice;
    }
}
