/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/26/24

 */

package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.OrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDataProcess implements OrderData{

    static String SAVE_ORDER = "INSERT INTO `order` (orderID, itemID, customerID, orderQty, totalPrice, orderDate) VALUES (?,?,?,?,?,?)";

    public boolean saveOrder(OrderDTO orderDTO, Connection connection) throws Exception{
        var ps = connection.prepareStatement(SAVE_ORDER);
        ps.setString(1, orderDTO.getId());
        ps.setString(2, orderDTO.getItemId());
        ps.setString(3, orderDTO.getCustomerId());
        ps.setString(4, orderDTO.getOrderQty());
        ps.setString(5, orderDTO.getTotalPrice());
        ps.setString(6, orderDTO.getOrderDate());
        return ps.executeUpdate() != 0;
    }
}
