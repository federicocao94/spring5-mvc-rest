package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private final String LASTNAME = "Jimmy";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }


    @Test
    public void getAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setLastname("Page");
        customer1.setFirstname("Mario");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setLastname("aaaa");
        customer2.setFirstname("Mabbbrio");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        //when
        when(customerService.getAllCustomers()).thenReturn(customers);

        //then
        mockMvc.perform(get("/api/v1/customers/")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }


    @Test
    public void getCustomerByLastname() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1L);
        customer.setLastname(LASTNAME);
        customer.setFirstname("Mario");

        //when
        when(customerService.getCustomerByLastname(LASTNAME)).thenReturn(customer);

        //then
        mockMvc.perform(get("/api/v1/customers/" + LASTNAME)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)));
    }
}
