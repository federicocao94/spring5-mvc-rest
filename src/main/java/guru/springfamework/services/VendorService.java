package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendor(Long id);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    void deleteVendor(Long id);

    VendorDTO saveVendorByDTO(Long id,VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id,VendorDTO vendorDTO);
}
