package com.dorin.restapplication.springboot;

import com.dorin.restapplication.springboot.domain.model.DuplicateEmployeeException;
import com.dorin.restapplication.springboot.domain.model.Employee;
import com.dorin.restapplication.springboot.domain.model.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DefaultEmployeeService implements EmployeeService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<Employee> employees;

    static {
        employees = populateDummyEmployees();
    }

    @Override
    public Optional<Employee> findById(final long id) {
        if (id < 0) {
            return Optional.empty();
        }

        return employees.stream()
                .filter(employee -> id == employee.getId())
                .findAny();
    }

    @Override
    public Optional<Employee> findByName(final String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            return Optional.empty();
        }

        return employees.stream()
                .filter(employee -> name.equals(employee.getName()))
                .findAny();
    }

    @Override
    public void saveEmployee(final Employee employee) throws DuplicateEmployeeException {
        if (Objects.isNull(employee)) {
            throw new IllegalArgumentException("Employee entity is null!");
        }

        boolean exists = employees.contains(employee);
        if (exists) {
            throw new DuplicateEmployeeException("Employee entity already exists!");
        }

        employees.add(employee);
    }

    @Override
    public void updateEmployee(final Employee employee) {
    }

    @Override
    public void deleteEmployee(final Employee employee) {
        if (Objects.isNull(employee)) {
            throw new IllegalArgumentException("Employee entity is null!");
        }
        employees.remove(employee);
    }

    @Override
    public List<Employee> findAllEmployee() {
        return employees;
    }

    private static List<Employee> populateDummyEmployees() {
        final List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(counter.incrementAndGet(), "Ion", "Stefan cel Mare", 300.5));
        employees.add(new Employee(counter.incrementAndGet(), "Grisha", "Bd. Moscova", 500.5));
        employees.add(new Employee(counter.incrementAndGet(), "Misha", "Db.Dacia", 450));
        employees.add(new Employee(counter.incrementAndGet(), "Aliona", "Mircea cel Batrin", 300));
        return employees;

    }
}
