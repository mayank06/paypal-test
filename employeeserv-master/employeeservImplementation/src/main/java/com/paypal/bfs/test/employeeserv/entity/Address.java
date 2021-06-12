package com.paypal.bfs.test.employeeserv.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private Integer employee_id;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_Line_2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zipcode")
    private Integer zipcode;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    Employee employee;

}
