package com.demo.useradmin1.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
@Entity
public class Address {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;

  private String address1;
  private String address2;
  private String city;
  private String state;
  private String zip;
  private String country;

  @JsonIgnore
  @ManyToMany(mappedBy = "addresses")
  private Set<User> users = new HashSet<>();
}
