/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/18/24

 */

package lk.ijse.aad.css_assignment_06_backend.persistance;

import lk.ijse.aad.css_assignment_06_backend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ItemDataProcess implements ItemData{

    static String SAVE_ITEM = "INSERT INTO item (id, name, price, qty) VALUES (?,?,?,?)";
    static String DELETE_ITEM = "DELETE FROM item WHERE id = ?";
    static String GET_ITEM_BY_ID = "SELECT * FROM item WHERE id = ?";
    static String GET_ALL_ITEMS = "SELECT * FROM item";
    static String UPDATE_ITEM = "UPDATE item SET name = ?, price = ?, qty = ? WHERE id = ?";
    static String UPDATE_ITEM_QUANTITY = "UPDATE item SET qty = ? WHERE id = ?";

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1, itemDTO.getId());
            ps.setString(2, itemDTO.getName());
            ps.setString(3, itemDTO.getPrice());
            ps.setString(4, itemDTO.getQty());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(DELETE_ITEM);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateItem(String id, ItemDTO itemDTO, Connection connection) throws Exception {
        var ps = connection.prepareStatement(UPDATE_ITEM);
        ps.setString(1, itemDTO.getName());
        ps.setString(2, itemDTO.getPrice());
        ps.setString(3, itemDTO.getQty());
        ps.setString(4, id);
        return ps.executeUpdate() != 0;
    }

    public boolean updateItemQuantity(String id, String qty, Connection connection) throws Exception {
        var ps = connection.prepareStatement(UPDATE_ITEM_QUANTITY);
        ps.setString(1, qty);
        ps.setString(2, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws Exception {
        var ps = connection.prepareStatement(GET_ITEM_BY_ID);
        ItemDTO itemDTO = new ItemDTO();
        ps.setString(1, id);
        var resultSet = ps.executeQuery();
        while (resultSet.next()) {
            itemDTO.setId(resultSet.getString(1));
            itemDTO.setName(resultSet.getString(2));
            itemDTO.setPrice(resultSet.getString(3));
            itemDTO.setQty(resultSet.getString(4));
        }
        return itemDTO;
    }

    public List<ItemDTO> getAllItems(Connection connection) throws Exception {
        List<ItemDTO> itemDTOS = new ArrayList<>();

        var ps = connection.prepareStatement(GET_ALL_ITEMS);
        var resultSet = ps.executeQuery();
        while (resultSet.next()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(resultSet.getString(1));
            itemDTO.setName(resultSet.getString(2));
            itemDTO.setPrice(resultSet.getString(3));
            itemDTO.setQty(resultSet.getString(4));
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }
}
