package gestionInventario.com.service.implementations;

import gestionInventario.com.mapper.custumer.CustomerMapper;
import gestionInventario.com.model.dto.customer.CustomerResponseDTO;
import gestionInventario.com.model.entity.Customer;
import gestionInventario.com.repository.ICustomerRepository;
import gestionInventario.com.service.interfaces.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {
    ICustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Override
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAll() {
        return customerMapper.customersToResponseDTO(customerRepository.findAll());
    }

    @Override
    public CustomerResponseDTO getCustomerWhitMostBuys() {
        return customerMapper.customerToCustomerResponseDTO(customerRepository.getCustomerWhitMostBuys().get());
    }

}
