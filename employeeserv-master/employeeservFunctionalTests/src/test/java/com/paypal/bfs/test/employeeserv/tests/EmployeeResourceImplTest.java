package com.paypal.bfs.test.employeeserv.tests;

import com.paypal.bfs.test.employeeserv.api.exceptions.ServiceException;
import com.paypal.bfs.test.employeeserv.entity.Address;
import com.paypal.bfs.test.employeeserv.entity.Employee;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepo;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeResourceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeResourceImpl employeeResource;

    @Test
    public void testAddEmployee() throws ServiceException {
        Employee employee = mockEmployee();
        Assert.assertNotNull(employee);
        employee = employeeRepo.save(employee);
        Assert.assertNotNull(employee);

    }

    @Test
    public void testAddEmployeeDuplicateRecord() {
        Employee employee = mockEmployee();
        Assert.assertNotNull(employee);
        employeeRepo.save(employee);
        Assert.assertTrue(employeeRepo.existsById(employee.getId()));
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = mockEmployee();
        Integer empId = employee.getId();
        employeeRepo.save(employee);

        Optional<Employee> emp = employeeRepo.findById(empId);
        Assert.assertEquals(empId, emp.get().getId());
    }

    @Test
    public void testEmployeeNotFound() {
        Employee employee = mockEmployee();
        Integer empId = 2;
        employeeRepo.save(employee);
        Assert.assertEquals(Optional.empty(), employeeRepo.findById(empId));
    }

    @Test(expected = NullPointerException.class)
    public void testThrowNPEWhenIdempotencyKeyNotPresent() throws ServiceException {
        employeeResource.addEmployee(null, new com.paypal.bfs.test.employeeserv.api.model.Employee());
    }

    @Test(expected = NullPointerException.class)
    public void testThrowNPEWhenEmployeeObjectNull() throws ServiceException {
        employeeResource.addEmployee(1, null);
    }

    private com.paypal.bfs.test.employeeserv.entity.Employee mockEmployee() {
        com.paypal.bfs.test.employeeserv.entity.Employee employee = new com.paypal.bfs.test.employeeserv.entity.Employee();
        employee.setId(1);
        employee.setFirstName("Mayank");
        employee.setLastName("Sinha");
        employee.setDob(LocalDate.parse("1989-11-16"));
        com.paypal.bfs.test.employeeserv.entity.Address address = new Address();
        address.setAddressLine1("Street 1");
        address.setAddressLine2("Anantpur");
        address.setCity("Ranchi");
        address.setState("Jharkhand");
        address.setCountry("India");
        address.setZipcode(834002);
        employee.setAddress(address);

        return employee;
    }
}
