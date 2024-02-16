package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTM {
    private int code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
