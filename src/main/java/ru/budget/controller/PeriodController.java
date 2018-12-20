package ru.budget.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.budget.domain.Category;
import ru.budget.domain.Transaction;
import ru.budget.repository.CategoryRepository;
import ru.budget.repository.TransactionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PeriodController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CurrentDays currentDays = new CurrentDays();

    private static final String PERIOD_PATH = "/period";

    @GetMapping(PERIOD_PATH)
    public String index(Model model){
        List<Transaction> transactions = transactionRepository.findAll();
        Long time = new Date().getTime();

        HashMap<String, Integer> tr = new HashMap<>();
        Integer total = 0;

        for (Transaction t : transactions) {
            Long tDate = new Date(t.getDate().getTime()).getTime();
            Long currentDate = currentDays.getToday().getTime();

            if (t.getValue() < 0) {
                if (tDate.equals(currentDate)) {
                    Integer categotyValue = tr.get(t.getName());
                    if (categotyValue == null) {
                        tr.put(t.getName(), t.getValue());
                    } else {
                        tr.put(t.getName(), t.getValue() + categotyValue);
                    }
                    total += t.getValue();
                }
            }
        }

        model.addAttribute("transactions", tr);
        model.addAttribute("chart", new ArrayList<Integer>(tr.values()));
        model.addAttribute("total", total);

        return "period";
    }


    @GetMapping("/periodLastWeek")
    public String lastWeek(Model model){
        List<Transaction> transactions = transactionRepository.findAll();
        Long time = new Date().getTime();

        HashMap<String, Integer> tr = new HashMap<>();
        Integer total = 0;

        for (Transaction t : transactions) {
            Long tDate = new Date(t.getDate().getTime()).getTime();
            Long currentDate = currentDays.getToday().getTime();
            Long currentLastWeekDate = currentDays.getLastWeek().getTime();

            if (t.getValue() < 0) {
                if (tDate <= currentDate && tDate >= currentLastWeekDate) {
                    Integer categotyValue = tr.get(t.getName());
                    if (categotyValue == null) {
                        tr.put(t.getName(), t.getValue());
                    } else {
                        tr.put(t.getName(), t.getValue() + categotyValue);
                    }
                    total += t.getValue();
                }
            }
        }

        model.addAttribute("transactions", tr);
        model.addAttribute("chart", new ArrayList<Integer>(tr.values()));
        model.addAttribute("total", total);

        return "period";
    }

    @GetMapping("/periodLastMonth")
    public String lastMonth(Model model){

        List<Transaction> transactions = transactionRepository.findAll();
        Long time = new Date().getTime();

        HashMap<String, Integer> tr = new HashMap<>();
        Integer total = 0;

        for (Transaction t : transactions) {
            Long tDate = new Date(t.getDate().getTime()).getTime();
            Long currentDate = currentDays.getToday().getTime();
            Long currentLastMonthDate = currentDays.getLastMonth().getTime();


            if (t.getValue() < 0) {
                if (tDate <= currentDate && tDate >= currentLastMonthDate) {
                    Integer categotyValue = tr.get(t.getName());
                    if (categotyValue == null) {
                        tr.put(t.getName(), t.getValue());
                    } else {
                        tr.put(t.getName(), t.getValue() + categotyValue);
                    }
                    total += t.getValue();
                }
            }
        }

        model.addAttribute("transactions", tr);
        model.addAttribute("chart", new ArrayList<Integer>(tr.values()));
        model.addAttribute("total", total);

        return "period";
    }


    @Getter
    @Setter
    private class CurrentDays {

        private Date today = setDates("today");
        private Date lastWeek = setDates("week");
        private Date lastMonth = setDates("month");

        private Date setDates(String day) {

            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            switch (day) {

                case "week":
                    c.add(Calendar.WEEK_OF_YEAR,  -1);
                    break;
                case "month":
                    c.add(Calendar.MONTH,  -1);
                    break;

            }

            return c.getTime();
        }


    }
    
}
