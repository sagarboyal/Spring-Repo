package org.ecommerce.app.controller;

import jakarta.validation.Valid;
import org.ecommerce.app.model.User;
import org.ecommerce.app.payload.address.AddressDTO;
import org.ecommerce.app.service.AddressService;
import org.ecommerce.app.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AuthUtil authUtil;


    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddressHandler(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(addressService.getAddressList());
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddressHandler(@PathVariable Long addressId){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(addressService.getAddressList(addressId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<AddressDTO>> getUserAddressHandler(){
        User user = authUtil.loggedInUser();
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(addressService.getUserAddressList(user));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> saveAddressHandler(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.saveAddress(addressDTO, user));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressHandler(@Valid @RequestBody AddressDTO addressDTO,
                                                           @PathVariable Long addressId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.updateAddress(addressId, addressDTO));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddressHandler(@PathVariable Long addressId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.deleteAddress(addressId));
    }
}
