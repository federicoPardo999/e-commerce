package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.customer.CustomerResponseDTO;
import gestionInventario.com.model.entity.Customer;

import java.util.List;

public interface ICustomerService {
     void createCustomer(Customer customer);
     List<CustomerResponseDTO> getAll();
     CustomerResponseDTO getCustomerWhitMostBuys();
}
