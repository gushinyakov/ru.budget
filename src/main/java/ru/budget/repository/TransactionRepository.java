package ru.budget.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.budget.domain.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional
    List<Transaction> deleteAllByCategory_Id(Long id);

    List<Transaction> findAllByCategory_Id(Long id);

}
