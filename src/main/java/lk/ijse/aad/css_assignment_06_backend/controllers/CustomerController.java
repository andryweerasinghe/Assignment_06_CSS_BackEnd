/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/17/24

 */

package lk.ijse.aad.css_assignment_06_backend.controllers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aad.css_assignment_06_backend.dto.CustomerDTO;
import lk.ijse.aad.css_assignment_06_backend.persistance.CustomerDataProcess;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customerController")
public class CustomerController extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
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
        var customerDataProcess = new CustomerDataProcess();
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            boolean saved = customerDataProcess.saveCustomer(customerDTO, connection);
            if (saved) {
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Customer not saved");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var customerId = req.getParameter("id");
        if (customerId == null || customerId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Customer ID is required");
            return;
        }
        var customerDataProcess = new CustomerDataProcess();
        try (var writer = resp.getWriter()){
            boolean deleted = customerDataProcess.deleteCustomer(customerId, connection);
            if (deleted) {
                writer.write("Customer deleted successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Customer not deleted");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var customerId = req.getParameter("id");
        var customerDataProcess = new CustomerDataProcess();

        try (var writer = resp.getWriter()){
            if (customerId == null || customerId.isEmpty()) {
                List<CustomerDTO> allCustomers = customerDataProcess.getAllCustomers(connection);
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(allCustomers, writer);
            } else {
                var customer = customerDataProcess.getCustomer(customerId, this.connection);
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(customer, writer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        var customerDataProcess = new CustomerDataProcess();
        try (var writer = resp.getWriter()){
            var customerId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            boolean updated = customerDataProcess.updateCustomer(customerId, customerDTO, connection);

            if (updated) {
                writer.write("Customer updated successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Customer not updated");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
