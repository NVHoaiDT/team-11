package Mapper.customerConvert;

import DTO.customerDTO.responseDTO.FurnitureOfOrderResponseDTO;
import business.Furniture;
import org.modelmapper.ModelMapper;

public class FurnitureConvert {
    private static ModelMapper modelMapper = new ModelMapper();

    public static FurnitureOfOrderResponseDTO convertToDTO(Furniture furniture ) {
        FurnitureOfOrderResponseDTO productOfOrderConvert = modelMapper.map(furniture, FurnitureOfOrderResponseDTO.class);
        return productOfOrderConvert;
    }
}
