package com.paypal.bfs.test.employeeserv.entity;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Employee")
@Entity
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Address address;*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address")
    private Address address;

}
