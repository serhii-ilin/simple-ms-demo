package com.demo.useradmin1;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.useradmin1.model.Address;
import com.demo.useradmin1.model.User;
import com.demo.useradmin1.repo.AddressRepo;
import com.demo.useradmin1.repo.UserRepo;

@Component
public class AppInitializer implements CommandLineRunner {
  private final UserRepo userRepo;
  private final AddressRepo addressRepo;

  public AppInitializer(UserRepo userRepo, AddressRepo addressRepo) {
    this.userRepo = userRepo;
    this.addressRepo = addressRepo;
  }

  @Override
  public void run(String... args) {

    Address ad0 = new Address();
    ad0.setAddress1("11 Main st");
    ad0.setCity("Fake town");
    ad0.setState("IN");
    ad0.setCountry("US");
    ad0.setZip("6666");

    User user0 =
        new User(
            null, "James", "Smith", "james.smith@example.com", null, Collections.singleton(ad0));
    userRepo.save(user0);

    Address ad1 = new Address();
    ad1.setAddress1("113 Main st");
    ad1.setCity("Stockholm");
    ad1.setState("NA");
    ad1.setCountry("SWEDEN");
    ad1.setZip("3333");

    User user1 =
        new User(
            null, "Peter", "Swang", "peter.swant@example.com", null, Collections.singleton(ad1));
    userRepo.save(user1);

    userRepo.save(user1);
  }
}
