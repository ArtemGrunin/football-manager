package ua.com.footballmanagerapp.service.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
