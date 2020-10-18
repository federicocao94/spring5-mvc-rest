package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorMapper vendorMapper;

    private VendorRepository vendorRepository;


    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }


    public List<VendorDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());

    }


    public VendorDTO getVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        vendorDTO.setVendorUrl(getVendorUrl(id));

        return vendorDTO;
    }


    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}
