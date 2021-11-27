package com.demo.useradmin1.grpc;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.useradmin1.repo.UserRepo;

@Service
public class UserAdminGrpcService implements UserAdminService {

  private final UserRepo userRepo;

  public UserAdminGrpcService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public AddUserResponse addUser(AddUserRequest addUserRequest) throws UserAdminServiceError {
    try {
      com.demo.useradmin1.model.User user = toModel(addUserRequest.getUser());
      userRepo.save(user);
      return AddUserResponse.newBuilder().setUser(fromModel(user)).build();
    } catch (RuntimeException re) {
      throw new UserAdminServiceError("UserAdminGrpcService::addUser " + re.getMessage());
    }
  }

  @Override
  public List<User> listUsers(ListUsersRequest req) throws UserAdminServiceError {
    try {
      String country = req.getCountry() == null ? null : req.getCountry().toString();
      List<com.demo.useradmin1.model.User> users =
          country != null ? userRepo.findByAddressesCountry(country) : userRepo.findAll();
      return users.stream().map(this::fromModel).collect(Collectors.toList());
    } catch (RuntimeException re) {
      throw new UserAdminServiceError("UserAdminGrpcService::listUsers " + re.getMessage());
    }
  }

  private User fromModel(com.demo.useradmin1.model.User model) {
    return User.newBuilder()
        .setId(model.getId())
        .setEmail(model.getEmail())
        .setFirstName(model.getFirstName())
        .setLastName(model.getLastName())
        .build();
  }

  private com.demo.useradmin1.model.User toModel(User user) {
    com.demo.useradmin1.model.User model = new com.demo.useradmin1.model.User();
    model.setId(user.getId());
    model.setLastName(user.getLastName() == null ? null : user.getLastName().toString());
    model.setFirstName(user.getFirstName() == null ? null : user.getFirstName().toString());
    model.setEmail(user.getEmail() == null ? null : user.getEmail().toString());
    return model;
  }

  @Override
  public void deleteUser(DeleteUserRequest deleteUserRequest) throws UserAdminServiceError {
    try {
      userRepo.deleteById(deleteUserRequest.getUserId());
    } catch (RuntimeException re) {
      throw new UserAdminServiceError("UserAdminGrpcService::deleteUserRequest " + re.getMessage());
    }
  }
}
