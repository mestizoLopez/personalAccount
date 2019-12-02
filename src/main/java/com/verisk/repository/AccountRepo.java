package com.verisk.repository;

import com.verisk.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {

  @Query(value = "SELECT a.id, a.amount,a.status FROM Account a " +
          "WHERE a.id =:id ")
  public Optional<?> customFindById(@Param("id") Long id);

}
