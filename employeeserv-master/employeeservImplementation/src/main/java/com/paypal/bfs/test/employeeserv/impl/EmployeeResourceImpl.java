package com.paypal.bfs.test.employeeserv.impl;

import static java.util.Objects.requireNonNull;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.exceptions.BadRequestException;
import com.paypal.bfs.test.employeeserv.api.exceptions.InternalServerErrorException;
import com.paypal.bfs.test.employeeserv.api.exceptions.ResourceNotFoundException;
import com.paypal.bfs.test.employeeserv.api.exceptions.ServiceException;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepo;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeResourceImpl.class);

    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) throws ServiceException {

        if (id == null) {
            LOG.error("Employee id missing in search");
            throw new BadRequestException("Employeed id is required key for search");
        }

        Optional<com.paypal.bfs.test.employeeserv.entity.Employee> entity = employeeRepo.findById(Integer.valueOf(id));

        if (!entity.isPresent()) {
            throw new ResourceNotFoundException("Employee searched for doesn't exist in records");
        }

        Employee e = mapEmployeeDtoToPojo(entity.get());
        LOG.info("Request processed for Employee {} ", id);

        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> addEmployee(Integer employeeKey, Employee employee) throws ServiceException {

        requireNonNull(employee, "employee");
        requireNonNull(employeeKey, "idempotency key");

        boolean employeeExists = employeeRepo.existsById(employeeKey);

        if (employeeExists) {
            LOG.info("Duplicate entry found for key {} ", employeeKey);
            return new ResponseEntity<>(employee, HttpStatus.CONFLICT);
        }

        try {
            com.paypal.bfs.test.employeeserv.entity.Employee e = com.paypal.bfs.test.employeeserv.entity.Employee.builder()
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .dob(parseDob(employee.getDob()))
                    .id(employeeKey)
                    .build();

            com.paypal.bfs.test.employeeserv.entity.Address address = com.paypal.bfs.test.employeeserv.entity.Address.builder()
                    .addressLine1(employee.getAddress().getLine1())
                    .addressLine2(employee.getAddress().getLine2())
                    .state(employee.getAddress().getState())
                    .city(employee.getAddress().getCity())
                    .country(employee.getAddress().getCountry())
                    .zipcode(employee.getAddress().getZipcode())
                    .employee_id(employeeKey)
                    .build();

            e.setAddress(address);
            employeeRepo.save(e);
        } catch (Exception ex) {
            LOG.error("Exception occurred in saving Employee record");
            throw new InternalServerErrorException("Exception occurred while saving employee record");
        }
        LOG.info("Record successfully saved with key {} ", employeeKey);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    /**
     * @param dob date of birth in yyyy-MM-dd format of type {@link String}
     * @return {@link LocalDate} object
     */
    private LocalDate parseDob(String dob) {
        return LocalDate.parse(dob);
    }

    /**
     * Maps Employee {@link com.paypal.bfs.test.employeeserv.entity.Employee}
     * entity to {@link Employee} pojo
     *
     * @param entity {@link com.paypal.bfs.test.employeeserv.entity.Employee}
     * @return Returns the {@link Employee} object
     */
    private Employee mapEmployeeDtoToPojo(com.paypal.bfs.test.employeeserv.entity.Employee entity) {

        Employee employee = new Employee();
        employee.setFirstName(entity.getFirstName());
        employee.setLastName(entity.getLastName());
        employee.setDob(String.valueOf(entity.getDob()));
        Address address = new Address();
        address.setLine1(entity.getAddress().getAddressLine1());
        address.setLine2(entity.getAddress().getAddressLine2());
        address.setZipcode(entity.getAddress().getZipcode());
        address.setCountry(entity.getAddress().getCountry());
        address.setCity(entity.getAddress().getCity());
        address.setState(entity.getAddress().getState());
        employee.setAddress(address);

        return employee;
    }
}
