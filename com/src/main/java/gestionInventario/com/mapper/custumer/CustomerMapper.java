package gestionInventario.com.mapper.custumer;

import gestionInventario.com.model.dto.customer.CustomerResponseDTO;
import gestionInventario.com.model.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerResponseDTO customerToCustomerResponseDTO(Customer customer){
        return CustomerResponseDTO
                .builder()
                .dni(customer.getDni())
                .address(customer.getAddress())
                .mail(customer.getMail())
                .username(customer.getMail())
                .build();
    }

    public List<CustomerResponseDTO> customersToResponseDTO(List<Customer> customers){
        return customers.stream().map(this::customerToCustomerResponseDTO).toList();
    }
}
