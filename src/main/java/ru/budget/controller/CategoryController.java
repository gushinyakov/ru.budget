package ru.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.budget.domain.Category;
import ru.budget.domain.Transaction;
import ru.budget.repository.CategoryRepository;
import ru.budget.repository.TransactionRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Controller
public class CategoryController {

    private static final String CATEGORY_PATH = "/category";
    private static final String MODEL_KEY_LIST_CATEGORY = "listCategory";
    private static final String MODEL_KEY_CATEGORY_INFO = "categoryInfo";
    private static final String MODEL_KEY_CATEGORY_HEADER = "categoryHeader";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping(CATEGORY_PATH)
    public String form(Model model) {
        model.addAttribute(MODEL_KEY_LIST_CATEGORY, saveCategoryToMap(categoryRepository.findAll()));

        /* Show category's transactions (if present) */
        if (! categoryRepository.findAll().isEmpty() ) {
            Long id = categoryRepository.findAll().get(0).getId();

            model.addAttribute(MODEL_KEY_CATEGORY_INFO, saveTransactionsToMap(transactionRepository.findAllByCategory_Id(id)));
            Optional<Category> category = categoryRepository.findById(id);
            model.addAttribute(MODEL_KEY_CATEGORY_HEADER, category.isPresent() ? category.get().getName() : null);
        }


        return "category";
    }
    
    @PostMapping(CATEGORY_PATH)
    public String formsave(@RequestParam(name = "category-add") String categoryName){
        categoryRepository.save(new Category(categoryName));

        return "redirect:" + CATEGORY_PATH;
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id){

        transactionRepository.deleteAllByCategory_Id(id);
        categoryRepository.deleteById(id);
        return "redirect:" + CATEGORY_PATH;
    }

    @GetMapping("/show/{id}")
    public String showTransactions(@PathVariable Long id,Model model) {
        model.addAttribute(MODEL_KEY_LIST_CATEGORY, saveCategoryToMap(categoryRepository.findAll()));
        model.addAttribute(MODEL_KEY_CATEGORY_INFO, saveTransactionsToMap(transactionRepository.findAllByCategory_Id(id)));

        Optional<Category> category = categoryRepository.findById(id);

        model.addAttribute(MODEL_KEY_CATEGORY_HEADER, category.isPresent() ? category.get().getName() : null);
        return "category";
    }

    /* List of categories -> mustache */
    private ArrayList<Map<String, Object>> saveCategoryToMap(Iterable<Category> categories) {

        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (Category category : categories) {
            Map<String, Object> stringObjectMap = new HashMap<>();

            stringObjectMap.put("id", category.getId());
            stringObjectMap.put("name", category.getName());

            maps.add(stringObjectMap);
        }

        return maps.size() > 0 ? maps : null;
    }

    /* List of transactions -> mustache */
    private ArrayList<Map<String, Object>> saveTransactionsToMap(Iterable<Transaction> transactions) {

        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Map<String, Object> stringObjectMap = new HashMap<>();

            stringObjectMap.put("name", transaction.getName());
            stringObjectMap.put("value", transaction.getValue());
            stringObjectMap.put("date", transaction.getDate());

            maps.add(stringObjectMap);
        }

        return maps.size() > 0 ? maps : null;
    }


}
