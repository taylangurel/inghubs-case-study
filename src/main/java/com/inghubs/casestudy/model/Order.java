package com.inghubs.casestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Notes:
 * @Enumerated is used with the EnumType. STRING, which means the enum value will be stored as a String in the database.
 * @Temporal Stores both the date and time (year, month, day, hour, minute, second).
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName; // ! TRY ONLY

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;

    private Double size;
    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}


