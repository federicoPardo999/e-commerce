package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.customer.CustomerResponseDTO;
import gestionInventario.com.model.entity.UserEntity;

import java.util.List;

public interface ICustomerService {
     void createCustomer(UserEntity customer);
//     List<CustomerResponseDTO> getAll();
     //CustomerResponseDTO getCustomerWhitMostBuys();
}
