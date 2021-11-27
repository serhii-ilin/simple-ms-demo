package com.demo.useradmin1.grpc;

import java.util.List;

import org.apache.avro.grpc.AvroGrpcClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAdminGrpcServiceIT {

  private ManagedChannel channel;
  private UserAdminService stub;
  private UserAdminService.Callback callbackStub;

  @BeforeEach
  public void setUp() {
    if (channel != null && !channel.isShutdown()) {
      channel.shutdownNow();
    }
    int port = 3333;
    channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
    stub = AvroGrpcClient.create(channel, UserAdminService.class);
    callbackStub = AvroGrpcClient.create(channel, UserAdminService.Callback.class);
  }

  @AfterEach
  public void tearDown() {
    channel.shutdownNow();
  }

  @Test
  public void testListAllUsers() throws UserAdminServiceError {
    log.info("START testListAllUsers");
    List<User> users = stub.listUsers(ListUsersRequest.newBuilder().build());
    log.info("Users testListAllUsers: " + users);
    log.info("DONE testListAllUsers");
  }

  @Test
  public void testAddUser() throws UserAdminServiceError {
    log.info("START testAddUser");
    User userToAdd =
        User.newBuilder()
            .setEmail("new@example.com")
            .setFirstName("firstname.new")
            .setLastName("lastname.new")
            .build();
    AddUserRequest addUserReq = AddUserRequest.newBuilder().setUser(userToAdd).build();
    AddUserResponse addUserRes = stub.addUser(addUserReq);
    log.info("User's ID: " + addUserRes.getUser().getId());
    List<User> users = stub.listUsers(ListUsersRequest.newBuilder().build());
    log.info("Users testAddUser: " + users);
    log.info("DONE testAddUser");
  }
}
