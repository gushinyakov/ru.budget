package ru.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        new App().fun("aasgdfa sdhashdas", 'h');
    }


    private Integer fun(String str, Character s) {

        Integer prevL = 0;
        Integer newL = 0;
        Integer position = 0;
        Integer count = 0;

        for (Character c : str.toCharArray()) {
            if (c.equals(s)) {
                newL ++;
            } else {
                if (newL > prevL) {
                    prevL = newL;
                    position = count - prevL;
                }
                newL = 0;
            }
            count ++;
        }

        return position;
    }
}
