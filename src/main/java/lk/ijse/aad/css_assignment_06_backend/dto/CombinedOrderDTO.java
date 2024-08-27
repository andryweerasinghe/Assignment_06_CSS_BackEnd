/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/27/24

 */

package lk.ijse.aad.css_assignment_06_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CombinedOrderDTO implements Serializable {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String totalPrice;
    private String itemId;
    private String orderQty;


}
