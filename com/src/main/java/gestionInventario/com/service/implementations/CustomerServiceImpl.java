package gestionInventario.com.service.implementations;

import gestionInventario.com.mapper.custumer.CustomerMapper;
import gestionInventario.com.model.dto.customer.CustomerResponseDTO;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.repository.IUserRepository;
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
    IUserRepository customerRepository;

    @Override
    public void createCustomer(UserEntity customer) {
        customerRepository.save(customer);
    }

//    @Override
//    public List<CustomerResponseDTO> getAll() {
//        return customerMapper.customersToResponseDTO(customerRepository.findUsersByRole());
//    }

//    @Override
//    public CustomerResponseDTO getCustomerWhitMostBuys() {
//        return customerMapper.customerToCustomerResponseDTO(customerRepository.getCustomerWhitMostBuys().get());
//    }

}
