package com.example;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

@Controller
class CustomerController {

    private final CustomerRepository customerRepository;

    CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Get("/customers")
    Iterable<Customer> all() {
        return this.customerRepository.findAll();
    }

    @Get("/customers/{name}")
    Iterable<Customer> byName(String name) {
        return this.customerRepository.findByName(name);
    }

    @Post("/customer")
    Customer save(@Body Customer customer) {
        return customerRepository.save(customer);
    }

}

@JdbcRepository(dialect = Dialect.H2)
interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Iterable<Customer> findByName(String name);
}

@MappedEntity
record Customer(@Id @GeneratedValue Integer id, String name) {}