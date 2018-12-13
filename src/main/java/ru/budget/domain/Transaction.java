package ru.budget.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter @Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date date;
    private Integer value;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Transaction(String name, Date date, Integer value, Category category) {
        this.name = name;
        this.date = date;
        this.value = value;
        this.category = category;
    }

    public Transaction() { }

    public Long getId() {
        return this.id;
    }
}
