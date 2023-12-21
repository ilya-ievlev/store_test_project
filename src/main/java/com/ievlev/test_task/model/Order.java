package com.ievlev.test_task.model;


import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    private Long id;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "modification_datetime")
    @NotNull
    @LastModifiedDate
    private Date modificationTime;


    @OneToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderedItem> orderedItemList;


    public Order(boolean paid, Date modificationTime, User user) {
        this.paid = paid;
        this.modificationTime = modificationTime;
        this.user = user;
    }

    public Order() {

    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", paid=" + paid +
                ", modificationTime=" + modificationTime +
                '}';
    }
}
