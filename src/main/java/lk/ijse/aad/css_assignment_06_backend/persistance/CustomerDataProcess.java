/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/17/24

 */

package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CustomerDataProcess implements CustomerData {

    static String SAVE_CUSTOMER = "INSERT INTO customer (id, name, address, phoneNumber) VALUES (?,?,?,?)";
    static String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
    static String GET_CUSTOMER = "SELECT * FROM customer WHERE id = ?";
    static String UPDATE_CUSTOMER = "UPDATE customer SET name = ?, address = ?, phoneNumber = ? WHERE id = ?";
    static String GET_ALL_CUSTOMERS = "SELECT * FROM customer";

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customerDTO.getId());
            ps.setString(2, customerDTO.getName());
            ps.setString(3, customerDTO.getAddress());
            ps.setString(4, customerDTO.getPhoneNumber());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(DELETE_CUSTOMER);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customerDTO, Connection connection) throws Exception {
        var ps = connection.prepareStatement(UPDATE_CUSTOMER);
        ps.setString(1, customerDTO.getName());
        ps.setString(2, customerDTO.getAddress());
        ps.setString(3, customerDTO.getPhoneNumber());
        ps.setString(4, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER);
            var customerDTO = new CustomerDTO();
            ps.setString(1, id);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customerDTO.setId(resultSet.getString(1));
                customerDTO.setName(resultSet.getString(2));
                customerDTO.setAddress(resultSet.getString(3));
                customerDTO.setPhoneNumber(resultSet.getString(4));
            }
            return customerDTO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CustomerDTO> getAllCustomers(Connection connection) throws Exception{

        List<CustomerDTO> customers = new ArrayList<>();

        try {
            var ps = connection.prepareStatement(GET_ALL_CUSTOMERS);
            var resultSet = ps.executeQuery();
            while (resultSet.next()){
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(resultSet.getString("id"));
                customerDTO.setName(resultSet.getString("name"));
                customerDTO.setAddress(resultSet.getString("address"));
                customerDTO.setPhoneNumber(resultSet.getString("phoneNumber"));

                customers.add(customerDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
