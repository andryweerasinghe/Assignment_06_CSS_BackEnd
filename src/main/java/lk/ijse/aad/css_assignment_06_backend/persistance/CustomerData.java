package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.CustomerDTO;

import java.sql.Connection;

public sealed interface CustomerData permits CustomerDataProcess {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws Exception;
    boolean deleteCustomer(String id, Connection connection) throws Exception;
    boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection) throws Exception;
    CustomerDTO getCustomer(String id, Connection connection) throws Exception;
}
