package com.demo.useradmin1.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.rest.core.annotation.RestResource;

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
@EqualsAndHashCode(of = "email")
@Entity
public class User {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  private Long id;

  @NotBlank(message = "first name is mandatory")
  @Column(nullable = false)
  private String firstName;

  @NotBlank(message = "last name is mandatory")
  @Column(nullable = false)
  private String lastName;

  // Assuming it's a unique business/natural key and changed rarely
  @Email
  @NotBlank(message = "email is mandatory")
  @Column(nullable = false, unique = true)
  private String email;

  private String password;

  //  @JsonIgnore
  // Don't cascade DELETE for many-to-many
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
  @RestResource(path = "addresses", rel = "addresses")
  @ManyToMany
  @JoinTable(
      name = "user_address",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "address_id"))
  private Set<Address> addresses = new HashSet<>();
}
