/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/27/24

 */

package lk.ijse.aad.css_assignment_06_backend.dto;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetailDTO implements Serializable {
    private String orderId;
    private String itemId;
    private String orderQuantity;
    private String orderDate;
    private String customerId;
    private String totalPrice;
    private String itemName;
    private String unitPrice;
    private String qtyOnHand;

    public OrderDetailDTO(String orderId, String itemId, String orderQuantity) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.orderQuantity = orderQuantity;
    }
}
