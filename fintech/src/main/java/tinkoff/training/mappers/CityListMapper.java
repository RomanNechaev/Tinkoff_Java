package tinkoff.training.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.models.CityDto;

import java.util.List;
public interface CityListMapper {

    List<City> toCityList(List<CityDto> dtos);
    List<CityDto> toDTOList(List<City> models);
}
