package com.example.demo.bo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author i565244
 */

@Entity
@Table(name="Cars")
@Data
public class Car{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;

}
