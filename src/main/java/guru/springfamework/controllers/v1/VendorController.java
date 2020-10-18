package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({VendorController.BASE_URL})
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO listVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO listVendors(@PathVariable Long id) {
        return vendorService.getVendor(id);
    }
}
