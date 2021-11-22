package com.demo.useradmin1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.useradmin1.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  List<User> findByAddressesCountry(String country);
}
