package tinkoff.training.mappers;

import org.mapstruct.Mapper;
import tinkoff.training.entities.City;
import tinkoff.training.models.CityDto;

import java.util.List;

@Mapper(uses = CityListMapper.class)
public interface CityListMapper {
    List<City> toCityList(List<CityDto> dtos);
    List<CityDto> toDTOList(List<City> models);
}
