package com.verisk.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @NotNull(message = "Pin is required")
  @Pattern(regexp = "^[1-9]{4}$", message = "Pin needs to have 4 digits and non zero")
  private String pin;

  @Column
  @NotNull(message = "Amount is required")
  @Min(value = 1,message = "Insert an amount")
  private BigDecimal amount;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;

  /*
  @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
  @BatchSize(size=3)
  private List<Transaction> transactions;
  */
  @Transient
  private List<Transaction> transactions;

  @Column
  @NotNull(message = "Type is required")
  @NotBlank(message = "Type is required")
  private String type;

  @Column
  @NotNull(message = "Status is required")
  @NotBlank(message = "Status is required")
  private String status;

}
