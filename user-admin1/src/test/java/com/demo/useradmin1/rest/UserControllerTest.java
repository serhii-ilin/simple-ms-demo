package com.demo.useradmin1.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.useradmin1.model.Address;
import com.demo.useradmin1.model.User;
import com.demo.useradmin1.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @MockBean private UserRepo userRepo;
  @Autowired private MockMvc mockMvc;

  private User u0;
  private User u1;
  private Address a0;
  private Address a1;

  @BeforeEach
  public void setUp() {
    // Create fresh addresses and users. Could be modified in the tests.
    a0 = new Address();
    a0.setId(1L);
    a0.setZip("a0.zip");
    a0.setCountry("a0.country");
    a0.setAddress1("a0.address1");
    a0.setAddress2("a0.address2");
    a0.setState("a0.state");
    a0.setCity("a0.city");

    a1 = new Address();
    a1.setId(2L);
    a1.setZip("a1.zip");
    a1.setCountry("a1.country");
    a1.setAddress1("a1.address1");
    a1.setAddress2("a1.address2");
    a1.setState("a1.state");
    a1.setCity("a1.city");

    u0 = new User();
    u0.setId(1L);
    u0.setFirstName("u0.firstName");
    u0.setLastName("u0.lastName");
    u0.setEmail("u0.email@example.com");
    u0.setPassword("u0.pass");
    u0.setAddresses(new HashSet<>());
    u0.getAddresses().add(a0);

    u1 = new User();
    u1.setId(2L);
    u1.setFirstName("u1.firstName");
    u1.setLastName("u1.lastName");
    u1.setEmail("u1.email@example.com");
    u1.setPassword("u1.pass");
    u1.setAddresses(new HashSet<>());
    u1.getAddresses().add(a1);
  }

  @Test
  public void testFinaAllUsersEmpty() throws Exception {
    mockMvc
        .perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void testFindAllUsers() throws Exception {
    when(userRepo.findAll()).thenReturn(List.of(u0, u1));

    final String EXPECTED_JSON_RESP =
        "[{\"id\":1,\"firstName\":\"u0.firstName\",\"lastName\":\"u0.lastName\",\"email\":\"u0.email@example.com\",\"password\":\"u0.pass\",\"addresses\":[{\"id\":1,\"address1\":\"a0.address1\",\"address2\":\"a0.address2\",\"city\":\"a0.city\",\"state\":\"a0.state\",\"zip\":\"a0.zip\",\"country\":\"a0.country\"}]},{\"id\":2,\"firstName\":\"u1.firstName\",\"lastName\":\"u1.lastName\",\"email\":\"u1.email@example.com\",\"password\":\"u1.pass\",\"addresses\":[{\"id\":2,\"address1\":\"a1.address1\",\"address2\":\"a1.address2\",\"city\":\"a1.city\",\"state\":\"a1.state\",\"zip\":\"a1.zip\",\"country\":\"a1.country\"}]}]";

    mockMvc
        .perform(get("/api/v1/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(EXPECTED_JSON_RESP));

    verify(userRepo, times(1)).findAll();
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testFindUsersByCountry() throws Exception {
    when(userRepo.findByAddressesCountry(Mockito.eq("u0.country"))).thenReturn(List.of(u0));

    final String EXPECTED_JSON_RESP =
        "[{\"id\":1,\"firstName\":\"u0.firstName\",\"lastName\":\"u0.lastName\",\"email\":\"u0.email@example.com\",\"password\":\"u0.pass\",\"addresses\":[{\"id\":1,\"address1\":\"a0.address1\",\"address2\":\"a0.address2\",\"city\":\"a0.city\",\"state\":\"a0.state\",\"zip\":\"a0.zip\",\"country\":\"a0.country\"}]}]";

    mockMvc
        .perform(get("/api/v1/users").param("country", "u0.country"))
        .andExpect(status().isOk())
        .andExpect(content().json(EXPECTED_JSON_RESP));

    verify(userRepo, times(1)).findByAddressesCountry(Mockito.eq("u0.country"));
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testUserByIdFound() throws Exception {
    when(userRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(u0));

    final String EXPECTED_JSON_RESP =
        "{\"id\":1,\"firstName\":\"u0.firstName\",\"lastName\":\"u0.lastName\",\"email\":\"u0.email@example.com\",\"password\":\"u0.pass\",\"addresses\":[{\"id\":1,\"address1\":\"a0.address1\",\"address2\":\"a0.address2\",\"city\":\"a0.city\",\"state\":\"a0.state\",\"zip\":\"a0.zip\",\"country\":\"a0.country\"}]}";

    mockMvc
        .perform(get("/api/v1/users/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(EXPECTED_JSON_RESP));

    verify(userRepo, times(1)).findById(Mockito.eq(1L));
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testUserByIdNotFound() throws Exception {
    when(userRepo.findById(Mockito.eq(1L))).thenReturn(Optional.empty());
    mockMvc
        .perform(get("/api/v1/users/1"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""))
        .andExpect(jsonPath("$").doesNotExist());

    verify(userRepo, times(1)).findById(Mockito.eq(1L));
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testNewUser() throws Exception {
    when(userRepo.save(Mockito.eq(u1))).thenReturn(u1);

    final String userAsJson = new ObjectMapper().writeValueAsString(u1);
    mockMvc
        .perform(
            post("/api/v1/users").content(userAsJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string(userAsJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("2"));

    verify(userRepo, times(1)).save(Mockito.eq(u1));
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testNewUserEmailValidationFailed() throws Exception {
    // When email is invalid
    u1.setEmail("---INVALID---");
    when(userRepo.save(Mockito.eq(u1))).thenReturn(u1);

    final String userAsJson = new ObjectMapper().writeValueAsString(u1);

    mockMvc
        .perform(
            post("/api/v1/users").content(userAsJson).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.violations[0].fieldName").value("email"));

    verifyNoInteractions(userRepo);
  }

  @Test
  public void testDeleteUser() throws Exception {
    Mockito.doNothing().when(userRepo).deleteById(Mockito.eq(1L));

    mockMvc
        .perform(delete("/api/v1/users/1"))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""))
        .andExpect(jsonPath("$").doesNotExist());

    verify(userRepo, times(1)).deleteById(Mockito.eq(1L));
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testUserAddresses() throws Exception {
    // Prepare a user with 2 addresses
    User usr = new User();
    usr.setAddresses(new HashSet<>());
    usr.getAddresses().add(a0);
    usr.getAddresses().add(a1);

    when(userRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(usr));

    final String EXPECTED_JSON_RESP =
        "[{\"id\":2,\"address1\":\"a1.address1\",\"address2\":\"a1.address2\",\"city\":\"a1.city\",\"state\":\"a1.state\",\"zip\":\"a1.zip\",\"country\":\"a1.country\"},{\"id\":1,\"address1\":\"a0.address1\",\"address2\":\"a0.address2\",\"city\":\"a0.city\",\"state\":\"a0.state\",\"zip\":\"a0.zip\",\"country\":\"a0.country\"}]";

    mockMvc
        .perform(get("/api/v1/users/1/addresses"))
        .andExpect(status().isOk())
        .andExpect(content().json(EXPECTED_JSON_RESP));

    verify(userRepo, times(1)).findById(Mockito.eq(1L));
    verifyNoMoreInteractions(userRepo);
  }

  @Test
  public void testDeleteAddressFromUser() throws Exception {
    when(userRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(u1));
    when(userRepo.save(Mockito.eq(u1))).thenReturn(u1);

    // there is an address with id = 2 in user with id = 1
    mockMvc
        .perform(delete("/api/v1/users/1/addresses/2"))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""))
        .andExpect(jsonPath("$").doesNotExist());

    verify(userRepo, times(1)).findById(Mockito.eq(1L));
    verify(userRepo, times(1)).save(Mockito.eq(u1));
    verifyNoMoreInteractions(userRepo);
    Assertions.assertTrue(u1.getAddresses().isEmpty());
  }

  @Test
  public void testAddAddressToUser() throws Exception {
    u1.getAddresses().clear();
    Assertions.assertTrue(u1.getAddresses().isEmpty());

    when(userRepo.findById(Mockito.eq(1L))).thenReturn(Optional.of(u1));
    when(userRepo.save(Mockito.eq(u1))).thenReturn(u1);

    final String addressAsJson = new ObjectMapper().writeValueAsString(a0);

    mockMvc
        .perform(
            post("/api/v1/users/1/addresses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(addressAsJson))
        .andExpect(content().string(""))
        //        .andExpect(status().isCreated())
        .andExpect(content().string(""))
        .andExpect(jsonPath("$").doesNotExist());

    verify(userRepo, times(1)).findById(Mockito.eq(1L));
    verify(userRepo, times(1)).save(Mockito.eq(u1));
    verifyNoMoreInteractions(userRepo);
    Assertions.assertFalse(u1.getAddresses().isEmpty());
  }
}
