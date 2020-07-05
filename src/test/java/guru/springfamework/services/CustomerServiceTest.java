package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private static final Long ID = 2L;

    private static final String NAME = "Jimmy";

    private static final String LASTNAME = "Page";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }


    @Test
    public void getAllCustomers() throws Exception {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        //when
        when(customerRepository.findAll()).thenReturn(customers);

        //then
        List<CustomerDTO> customersDTO = customerService.getAllCustomers();

        assertEquals(3, customersDTO.size());
    }


    @Test
    public void getCustomerByLastname() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(NAME);
        customer.setLastname(LASTNAME);

        //when
        when(customerRepository.findByLastname(LASTNAME)).thenReturn(customer);

        //then
        CustomerDTO customerDTO = customerService.getCustomerByLastname(LASTNAME);

        assertEquals(ID, customerDTO.getId());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }
}
