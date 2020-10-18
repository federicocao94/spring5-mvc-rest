package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }


    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> VendorDTOs = vendorService.getAllVendors();

        //then
        assertEquals(2, VendorDTOs.size());
    }


    @Test
    public void getVendor() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName("vendor1");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendor(1L);

        //then
        assertEquals("vendor1", vendorDTO.getName());
    }


    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("vendor1");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(savedVendorDTO.getName(), vendorDTO.getName());
    }


    @Test
    public void testDeleteVendor() {
        Long id = 1L;

        vendorService.deleteVendor(id);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }


    @Test
    public void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("vendor1");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(1L, vendorDTO);

        //then
        assertEquals(savedVendorDTO.getName(), vendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedVendorDTO.getVendorUrl());
    }
}
