/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/26/24

 */

package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.OrderDTO;
import lk.ijse.aad.css_assignment_06_backend.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDataProcess implements OrderData{

    static String SAVE_ORDER = "INSERT INTO orders (orderId, customerId, orderDate, totalPrice) VALUES (?,?,?,?)";
    static String SAVE_ORDER_DETAILS = "INSERT INTO orderDetails (orderId, itemId, orderQty) VALUES (?,?,?)";
    static String GET_ORDER_DETAILS = "SELECT o.orderId, od.itemId, i.name AS itemName, i.price AS unitPrice, i.qty AS qtyOnHand, od.orderQty AS qty, o.orderDate, o.customerId, o.totalPrice FROM orders o JOIN orderDetails od ON o.orderId = od.orderId JOIN item i ON od.itemId = i.id";
    static String DELETE_ORDER = "DELETE FROM orders WHERE orderId = ?";
    static String DELETE_ORDER_DETAILS = "DELETE FROM orderDetails WHERE orderId = ?";

    public boolean saveOrder(OrderDTO orderDTO, Connection connection) throws Exception{
        var ps = connection.prepareStatement(SAVE_ORDER);
        ps.setString(1, orderDTO.getOrderId());
        ps.setString(2, orderDTO.getCustomerId());
        ps.setString(3, orderDTO.getOrderDate());
        ps.setString(4, orderDTO.getTotalPrice());
        return ps.executeUpdate() != 0;
    }

    public boolean saveOrderDetails(OrderDetailDTO orderDetailDTO, Connection connection) throws Exception{
        var ps = connection.prepareStatement(SAVE_ORDER_DETAILS);
        ps.setString(1, orderDetailDTO.getOrderId());
        ps.setString(2, orderDetailDTO.getItemId());
        ps.setString(3, orderDetailDTO.getOrderQuantity());
        return ps.executeUpdate() != 0;
    }

    public boolean deleteOrder(String orderId, Connection connection) throws Exception{
        try {
            var ps = connection.prepareStatement(DELETE_ORDER);
            ps.setString(1, orderId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrderDetails(String orderId, Connection connection) throws Exception{
        try {
            var ps = connection.prepareStatement(DELETE_ORDER_DETAILS);
            ps.setString(1, orderId);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderDetailDTO> getOrderDetails(Connection connection) throws Exception {
        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        // Prepare the SQL statement
        try (var ps = connection.prepareStatement(GET_ORDER_DETAILS);
             var rs = ps.executeQuery()) {

            // Process the result set
            while (rs.next()) {
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setOrderId(rs.getString("orderId"));
                orderDetailDTO.setItemId(rs.getString("itemId"));
                orderDetailDTO.setItemName(rs.getString("itemName"));
                orderDetailDTO.setUnitPrice(rs.getString("unitPrice"));
                orderDetailDTO.setQtyOnHand(rs.getString("qtyOnHand"));
                orderDetailDTO.setOrderQuantity(rs.getString("qty"));
                orderDetailDTO.setOrderDate(rs.getString("orderDate"));
                orderDetailDTO.setCustomerId(rs.getString("customerId"));
                orderDetailDTO.setTotalPrice(rs.getString("totalPrice"));
                orderDetailDTOS.add(orderDetailDTO);
            }
        } catch (SQLException e) {
            // Log the SQL exception and rethrow
            System.err.println("SQL error occurred while fetching order details: " + e.getMessage());
            throw e;
        }

        return orderDetailDTOS;
    }

}
