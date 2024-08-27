package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.OrderDTO;
import lk.ijse.aad.css_assignment_06_backend.dto.OrderDetailDTO;

import java.sql.Connection;
import java.util.List;

public interface OrderData {
    boolean saveOrder(OrderDTO orderDTO, Connection connection) throws Exception;
    boolean saveOrderDetails(OrderDetailDTO orderDetailDTO, Connection connection) throws Exception;
    List<OrderDetailDTO> getOrderDetails(Connection connection) throws Exception;
    boolean deleteOrder(String orderId, Connection connection) throws Exception;
    boolean deleteOrderDetails(String orderId, Connection connection) throws Exception;
    boolean updateOrder(String orderId, OrderDTO orderDTO, Connection connection) throws Exception;
    boolean updateOrderDetails(String orderId, OrderDetailDTO orderDetailDTO, Connection connection) throws Exception;
}
