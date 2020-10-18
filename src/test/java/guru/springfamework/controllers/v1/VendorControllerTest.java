package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

public class VendorControllerTest extends AbstractRestControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }


    @Test
    public void testListVendors() throws Exception{
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("vendor1");
        vendor1.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendor2 = new VendorDTO();
        vendor1.setName("vendor2");
        vendor1.setVendorUrl(VendorController.BASE_URL + "/2");

        //when
        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));

        //then
        mockMvc.perform(get(VendorController.BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.vendors", hasSize(2)));
    }


    @Test
    public void testGetVendor() throws Exception{
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("vendor1");

        //when
        when(vendorService.getVendor(1L)).thenReturn(vendor1);

        //then
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("vendor1")));
    }


    @Test
    public void testCreateVendor() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("vendor1");

        VendorDTO savedVendorDTO = new VendorDTO();
        savedVendorDTO.setName(vendorDTO.getName());
        savedVendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        //when
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(savedVendorDTO);

        //then
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())))
                .andExpect(jsonPath("vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }


    @Test
    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendor(anyLong());
    }

}
