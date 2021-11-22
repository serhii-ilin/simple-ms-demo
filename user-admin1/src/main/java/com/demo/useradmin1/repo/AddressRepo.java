package com.demo.useradmin1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.useradmin1.model.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {}
