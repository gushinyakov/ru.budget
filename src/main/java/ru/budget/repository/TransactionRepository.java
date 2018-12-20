package ru.budget.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.budget.domain.Transaction;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional
    void deleteAllByCategory_Id(Long id);

    List<Transaction> findAllByCategory_Id(Long id);

    List<Transaction> findByDate(Date date);

    List<Transaction> findByDateBeforeAndDateAfter(Date dateFrom, Date dateTo);


}
