package com.ievlev.test_task.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Data
@Table(name = "ordered_items")
public class OrderedItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    private Long id;

    @Min(0)
    @Column(name = "price")
    private int price;

    @Min(1)
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "available_item_id")
    private AvailableItem availableItem;

    public OrderedItem(@Min(1) Long id, @Min(0) int price, @Min(1) int quantity, @Valid Order order, @Valid AvailableItem availableItem) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.availableItem = availableItem;
    }

    public OrderedItem(@Min(0) int price, @Min(1) int quantity, @Valid Order order, @Valid AvailableItem availableItem) {
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.availableItem = availableItem;
    }

    public OrderedItem() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedItem that = (OrderedItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
