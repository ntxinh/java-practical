package example.controller;

import example.Customer;
import example.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public Iterable<Customer> list() {
        return customerRepository.findAll();
    }

    @PostMapping
    public void create(@RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        customerRepository.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id
            , @RequestBody Customer customer) {
        Customer customer_update = customerRepository.findOne(id);
        customer_update.setName(customer.getName());
        customer_update.setAge(customer.getAge());
        customerRepository.save(customer_update);
    }
}
