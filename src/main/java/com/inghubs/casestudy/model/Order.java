package com.inghubs.casestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Notes:
 * Enumerated is used with the EnumType. STRING, which means the enum value will be stored as a String in the database.
 * Temporal Stores both the date and time (year, month, day, hour, minute, second).
 */

@Entity
@Table(name = "customer_order") //Since "order" is a reserved keyword, we change the table name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;

    private Double size;
    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // * The @Temporal annotation helps specify whether you're storing just the date, just the time, or the full timestamp. In our case the full timestamp
    // * JPA uses this annotation to correctly map the date and time values from Java to the appropriate SQL data types, such as DATE, TIME, or TIMESTAMP.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}


