package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Vendor Data" + vendorRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run(); //load data

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }


    @Test
    public void testPatchVendor() {
        String updatedName = "new name";
        Long id = findVendorId();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).get();

        assertEquals(updatedVendor.getName(), updatedName);
    }


    @Test
    public void testPatchVendor2() {
        Long id = findVendorId();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(null);

        String originalName = originalVendor.getName();

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).get();

        assertEquals(updatedVendor.getName(), originalName);
    }


    private Long findVendorId() {
        List<Vendor> vendors = vendorRepository.findAll();

        return vendors.get(0).getId();
    }
}
