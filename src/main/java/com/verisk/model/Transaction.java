package com.verisk.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "{date}", allowGetters = true)
public class Transaction implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn
  @JsonIgnore
  private User user;

/*
  @ManyToOne
  @JoinColumn
  @JsonIgnore
  private Account account;
*/
  private Long accountId;

  @CreatedDate
  @Temporal(TemporalType.DATE)
  private Date date = new Date();

  @Column
  @NotNull(message = "Type Is Mandatory")
  @NotBlank(message = "Type Is Mandatory")
  private String type;

  @Column
  @NotNull(message = "Description Is Mandatory")
  @NotBlank(message = "Description Is Mandatory")
  private String description;

  @Column
  @NotNull(message = "Amount Is Mandatory")
  @Min(value = 1,message = "Insert an amount")
  private BigDecimal amount;


}
