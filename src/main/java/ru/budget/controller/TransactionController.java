package ru.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.budget.domain.Category;
import ru.budget.domain.Transaction;
import ru.budget.repository.CategoryRepository;
import ru.budget.repository.TransactionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String TRANSACTION_PATH = "/transaction";

    @GetMapping
    public String index(Model model){
        model.addAttribute("categoryOptions", categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList()));
        model.addAttribute("transactions", showAllTransactions(transactionRepository.findAll()));
        return "transaction";
    }

    @GetMapping(TRANSACTION_PATH)
    public String transaction(Model model) {
        model.addAttribute("categoryOptions", categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList()));
        model.addAttribute("transactions", showAllTransactions(transactionRepository.findAll()));
        return "transaction";
    }

    @PostMapping("/addTransaction")
    public String addTransaction(
            @RequestParam(name = "transaction-date") String date,
            @RequestParam(name = "transaction-name") String name,
            @RequestParam(name = "transaction-value") Integer value,
            @RequestParam(name = "transaction-category")String category,
            Model model
            ) throws ParseException {

        if (!category.equals("default")) {
            Transaction transaction = new Transaction(
                    name,
                    new SimpleDateFormat("yyyy-mm-dd").parse(date),
                    value,
                    categoryRepository.findByName(category)
            );
            transactionRepository.save(transaction);
        }

        model.addAttribute("transactions", showAllTransactions(transactionRepository.findAll()));


        return "redirect:" + TRANSACTION_PATH;
    }


    private HashMap<String, HashMap<String, List<Map<String, String>>>> showAllTransactions(Iterable<Transaction> transactions) {

        HashMap<String, HashMap<String, List<Map<String, String>>>> map = new HashMap<>();

        Integer totalSum = 0;

        for (Transaction transaction : transactions) {

            /* Transaction params */
            String name = transaction.getName();
            String date = String.valueOf(transaction.getDate()).substring(0, 10);
            String category = transaction.getCategory().getName();
            Integer value = transaction.getValue();

            /* Increment total sum */
            totalSum += value;

            HashMap<String, List<Map<String, String>>> categoriesOnDate = map.getOrDefault(date, new HashMap<>());

            List<Map<String, String>> listTransactions = categoriesOnDate.getOrDefault(category, new ArrayList<>());

            Map<String, String> transactionMap = new HashMap<>();

            transactionMap.put(name, String.valueOf(value));

            listTransactions.add(transactionMap);

            categoriesOnDate.put(category, listTransactions);

            map.put(date, categoriesOnDate);

        }

        return map;

    }
}
