package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.exceptions.ServiceException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.validator.ValidEmployee;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    @GetMapping("/v1/bfs/employees/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") @NotNull String id) throws ServiceException;

    /**
     * Creates an employee record with unique idempotency-key
     *
     * @param employeeKey {@linkplain Idempotency-Key(Unique)} for creating employee object
     * @param employee    {@link Employee} object
     * @return {@link Employee} resource.
     */
    @PostMapping("/v1/bfs/employees")
    ResponseEntity<Employee> addEmployee(@RequestHeader("Idempotency-Key") @NotNull Integer employeeKey,
            @RequestBody @NotNull @Valid @ValidEmployee Employee employee) throws ServiceException;

}
