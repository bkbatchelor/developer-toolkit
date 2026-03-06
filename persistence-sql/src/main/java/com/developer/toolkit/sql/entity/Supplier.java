package com.developer.toolkit.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplier")
@Getter
@Setter
public class Supplier {

    @Id
    private Long id;

    private String name;

    @Column(name = "contact_info")
    private String contactInfo;

    private String address;

    @Column(name = "lead_times")
    private String leadTimes;

}
