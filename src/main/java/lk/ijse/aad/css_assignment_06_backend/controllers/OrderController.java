/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/26/24

 */

package lk.ijse.aad.css_assignment_06_backend.controllers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aad.css_assignment_06_backend.dto.CombinedOrderDTO;
import lk.ijse.aad.css_assignment_06_backend.dto.OrderDTO;
import lk.ijse.aad.css_assignment_06_backend.dto.OrderDetailDTO;
import lk.ijse.aad.css_assignment_06_backend.persistance.OrderDataProcess;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orderController")
public class OrderController extends HttpServlet {

    Connection connection;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/Assignment_06");
            this.connection = pool.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        var orderDataProcess = new OrderDataProcess();
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            CombinedOrderDTO combinedOrderDTO = jsonb.fromJson(req.getReader(), CombinedOrderDTO.class);

            var orderId = combinedOrderDTO.getOrderId();
            var customerId = combinedOrderDTO.getCustomerId();
            var orderDate = combinedOrderDTO.getOrderDate();
            var totalPrice = combinedOrderDTO.getTotalPrice();
            var itemId = combinedOrderDTO.getItemId();
            var orderQty = combinedOrderDTO.getOrderQty();

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(orderId);
            orderDTO.setCustomerId(customerId);
            orderDTO.setOrderDate(orderDate);
            orderDTO.setTotalPrice(totalPrice);

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setOrderId(orderId);
            orderDetailDTO.setItemId(itemId);
            orderDetailDTO.setOrderQuantity(orderQty);

            boolean saved = orderDataProcess.saveOrder(orderDTO, connection);
            boolean saved1 = orderDataProcess.saveOrderDetails(orderDetailDTO, connection);
            if (saved && saved1) {
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Order not saved");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDataProcess orderDataProcess = new OrderDataProcess();

        try (var writer = resp.getWriter()){
            List<OrderDetailDTO> allOrders = orderDataProcess.getOrderDetails(connection);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(allOrders, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
