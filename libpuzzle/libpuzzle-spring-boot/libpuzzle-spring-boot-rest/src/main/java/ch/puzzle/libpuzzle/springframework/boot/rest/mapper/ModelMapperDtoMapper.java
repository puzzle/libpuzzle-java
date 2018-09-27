package ch.puzzle.libpuzzle.springframework.boot.rest.mapper;

import org.modelmapper.ModelMapper;

public class ModelMapperDtoMapper implements DtoMapper {

    private ModelMapper mapper;

    public ModelMapperDtoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <TDestination> void map(Object source, TDestination destination) {
        mapper.map(source, destination);
    }

    @Override
    public <TDestination> TDestination map(Object source, Class<TDestination> destinationClass) {
        return mapper.map(source, destinationClass);
    }
}
