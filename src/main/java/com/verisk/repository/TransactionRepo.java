package com.verisk.repository;

import com.verisk.model.Account;
import com.verisk.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

  public List<Transaction> findTop5ByAccountIdOrderByDateDesc(Long id);

  public Optional<Transaction> findByAccountId(Long id);

}
