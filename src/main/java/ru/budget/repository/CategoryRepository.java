package ru.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.budget.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
