package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Address;
import org.ecommerce.app.model.User;
import org.ecommerce.app.payload.address.AddressDTO;
import org.ecommerce.app.repository.AddressRepository;
import org.ecommerce.app.repository.UserRepository;
import org.ecommerce.app.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        address = addressRepository.save(address);
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressList() {
        return addressRepository.findAll().stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getAddressList(Long addressId) {
        return modelMapper.map(addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId)), AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddressList(User user) {
        return user.getAddresses().stream()
                .map( address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        Optional.ofNullable(addressDTO.getStreet()).ifPresent(address::setStreet);
        Optional.ofNullable(addressDTO.getBuilding()).ifPresent(address::setBuilding);
        Optional.ofNullable(addressDTO.getCity()).ifPresent(address::setCity);
        Optional.ofNullable(addressDTO.getState()).ifPresent(address::setState);
        Optional.ofNullable(addressDTO.getCountry()).ifPresent(address::setCountry);
        Optional.ofNullable(addressDTO.getZipcode()).ifPresent(address::setZipcode);

        address = addressRepository.save(address);
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public AddressDTO deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        addressRepository.delete(address);
        return modelMapper.map(address, AddressDTO.class);
    }

}
