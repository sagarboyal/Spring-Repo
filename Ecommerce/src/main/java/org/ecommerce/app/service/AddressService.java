package org.ecommerce.app.service;

import org.ecommerce.app.model.User;
import org.ecommerce.app.payload.address.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO saveAddress(AddressDTO addressDTO, User user);
    List<AddressDTO> getAddressList();
    AddressDTO getAddressList(Long addressId);
    List<AddressDTO> getUserAddressList(User user);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    AddressDTO deleteAddress(Long addressId);
}
