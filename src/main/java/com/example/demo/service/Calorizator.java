package com.example.demo.service;

import com.example.demo.entities.LineShoppingList;
import com.example.demo.entities.Product;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@NoArgsConstructor
@Component
public class Calorizator {
   private ExpressionService expressionService;

    private double sumKcal;
    private double sumProtein;
    private double sumFat;
    private double sumCarbohydrates;

    private double proportionProtein;
    private double proportionFat;
    private double proportionCarbohydrates;

    private String report;
    private int quantity;

    public String getReport() {
        return report;
    }

    public void set (ArrayList<LineShoppingList> lines) {
       Product currentProduct;
       expressionService = new ExpressionService();
       for (LineShoppingList line :lines){
           currentProduct = line.getProduct();
           this.quantity = line.getQuantity();
           sumKcal += sum(currentProduct.getKcal());
           sumProtein += sum(currentProduct.getProtein());
           sumFat += sum(currentProduct.getFat());
           sumCarbohydrates += sum(currentProduct.getCarbohydrates());
       }
       this.proportionProtein = proportion(sumProtein, sumCarbohydrates, sumFat);
       this.proportionFat = proportion(sumFat, sumCarbohydrates, sumProtein);
       this.proportionCarbohydrates = proportion(sumCarbohydrates, sumFat, sumProtein);
       createReport();
       this.report = "ЕХААА";
   }

   private void createReport () {
       /* this. report = String.format("""
               Продукты в заданом списке покупок содержат:
               %s каллорий, %s белков, %s жиров, %s углеводов.
               
               Соотношение БЖУ:
               %s% белков
               %s% жиров
               %s% углеводов
               """, sumKcal, sumProtein, sumFat, sumCarbohydrates,
               proportionProtein, proportionFat, proportionCarbohydrates);*/
   }
    private double proportion (double desired, double second, double third) {
        return expressionService.calculate(
                String.format("%s/(%s+%s+%s)*100",
                        desired,
                        desired,
                        second,
                        third));
    }

   private double sum (double number) {
       return expressionService.calculate(
               String.format("%s/100*%d",
                       number,
                       this.quantity));
   }


}
