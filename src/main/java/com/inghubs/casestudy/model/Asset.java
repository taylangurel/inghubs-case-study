package com.inghubs.casestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class annotated with @Entity is recognized by the JPA framework as a representation of a table in a relational database, where each instance of the class corresponds to a row in the table.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String assetName;
    private Double size;
    private Double usableSize;
}

