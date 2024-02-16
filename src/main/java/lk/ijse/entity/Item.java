package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity(name = "item")
public class Item {

    @Id
    @Column(name = "item_code")
    private int code;

    @Column(name = "item_description")
    private String description;

    @Column(name = "item_unit_price")
    private double unit_price;

    @Column(name = "item_qty_on_hand")
    private int qty_on_hand;
}
