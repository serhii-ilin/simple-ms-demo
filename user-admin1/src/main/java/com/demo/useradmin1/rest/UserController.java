package com.demo.useradmin1.rest;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.useradmin1.model.Address;
import com.demo.useradmin1.model.User;
import com.demo.useradmin1.repo.UserRepo;

@RequestMapping("/api/v1/")
@RestController
public class UserController {

  private final UserRepo userRepo;

  public UserController(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @GetMapping("/users")
  public List<User> listAllUsers(@RequestParam(required = false) Optional<String> country) {
    // FIXME: handle parameters, like address??
    // FIXME: add unit tests
    // FIXME HETEAOS
    return country.isPresent()
        ? userRepo.findByAddressesCountry(country.get())
        : userRepo.findAll();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/users")
  public User newUser(@RequestBody @Valid User newUser) {
    return userRepo.save(newUser);
  }

  @GetMapping("/users/{id}")
  public User getUser(@PathVariable Long id) {
    return userRepo
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found User " + id));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable Long id) {
    userRepo.deleteById(id);
  }

  @GetMapping("/users/{id}/addresses")
  public Collection<Address> listUserAddresses(@PathVariable Long id) {
    return userRepo
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found User " + id))
        .getAddresses();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/users/{userId}/addresses")
  public void addAddressToUser(@PathVariable Long userId, @RequestBody @Valid Address address) {
    User user =
        userRepo
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found User " + userId));
    user.getAddresses().add(address);
    userRepo.save(user);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/users/{userId}/addresses/{addressId}")
  public void deleteAddressFromUser(@PathVariable Long userId, @PathVariable Long addressId) {
    User user =
        userRepo
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found User " + userId));

    Address addressToRemove =
        user.getAddresses().stream()
            .filter(address -> Objects.equals(address.getId(), addressId))
            .findFirst()
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Not found Address " + addressId));

    user.getAddresses().remove(addressToRemove);
    userRepo.save(user);
  }
}
