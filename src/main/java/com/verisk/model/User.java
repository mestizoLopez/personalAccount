package com.verisk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @NotNull(message = "Name Is Mandatory")
  @NotBlank(message = "Name Is Mandatory")
  private String name;

  @Column
  @NotNull(message = "lastName Is Mandatory")
  @NotBlank(message = "lastName Is Mandatory")
  private String lastName;
/*
  @Column
  @NotNull(message = "Password Is Mandatory")
  @NotBlank(message = "Password Is Mandatory")
  private String password;

  @Column
  @NotNull(message = "Role Is Mandatory")
  @NotBlank(message = "Role Is Mandatory")
  private String role;

  @Column
  @NotNull(message = "Email Is Mandatory")
  @NotBlank(message = "Email Is Mandatory")
  private String email;
*/
  @Column
  private String ssn;

  @Column
  private String voteCard;

  @ToString.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Transaction> transactions;

}
