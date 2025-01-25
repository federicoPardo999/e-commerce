package gestionInventario.com.mapper.custumer;

import gestionInventario.com.model.dto.customer.CustomerResponseDTO;
import gestionInventario.com.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerResponseDTO customerToCustomerResponseDTO(UserEntity customer){
        return CustomerResponseDTO
                .builder()
                .address(customer.getAddress())
                .mail(customer.getMail())
                .username(customer.getMail())
                .build();
    }

    public List<CustomerResponseDTO> customersToResponseDTO(List<UserEntity> customers){
        return customers.stream().map(this::customerToCustomerResponseDTO).toList();
    }
}
