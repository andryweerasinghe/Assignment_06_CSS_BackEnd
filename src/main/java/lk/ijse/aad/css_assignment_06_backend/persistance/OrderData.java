package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.OrderDTO;

import java.sql.Connection;

public interface OrderData {
    boolean saveOrder(OrderDTO orderDTO, Connection connection) throws Exception;
}
