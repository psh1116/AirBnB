package airbnb.persistence.mapper;

import airbnb.persistence.dto.HouseDTO;

import java.util.List;

public interface HouseMapper {
    HouseDTO getHouseByName(String houseName);

    List<HouseDTO> getHouseByAmenities(List<String> amenities);

    void insertHouse(HouseDTO houseDTO);
}
