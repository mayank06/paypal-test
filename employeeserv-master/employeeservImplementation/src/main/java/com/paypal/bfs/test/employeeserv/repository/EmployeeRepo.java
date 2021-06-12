package com.paypal.bfs.test.employeeserv.repository;

import com.paypal.bfs.test.employeeserv.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
