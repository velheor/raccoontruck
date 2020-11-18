package com.raccoontruck.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> findAll() {
        return convertToDTO(customerRepository.findAll());
    }

    @Override
    public CustomerDTO findById(Long id) {
        return convertToDTO(customerRepository.findById(id).orElse(null));
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        customerRepository.delete(convertFromDTO(findById(id)));
    }

    @Override
    public CustomerDTO convertToDTO(Customer customer) {
        return ObjectMapperUtils.map(customer, CustomerDTO.class);
    }

    @Override
    public Customer convertFromDTO(CustomerDTO customerDTO) {
        return ObjectMapperUtils.map(customerDTO, Customer.class);
    }

    @Override
    public List<CustomerDTO> convertToDTO(List<Customer> customers) {
        return ObjectMapperUtils.mapAll(customers, CustomerDTO.class);
    }

    @Override
    public List<Customer> convertFromDTO(List<CustomerDTO> customerDTOS) {
        return ObjectMapperUtils.mapAll(customerDTOS, Customer.class);
    }
}