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

    private int quantity;



    public void set (ArrayList<LineShoppingList> lines) {
       reload();
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
       if (sumProtein == 0 && sumCarbohydrates == 0 && sumFat == 0){
           sumCarbohydrates = 0.01;
       }
       this.proportionProtein = proportion(sumProtein, sumCarbohydrates, sumFat);
       this.proportionFat = proportion(sumFat, sumCarbohydrates, sumProtein);
       this.proportionCarbohydrates = proportion(sumCarbohydrates, sumFat, sumProtein);

   }

    public void reload() {
        this.sumKcal = 0;
        this.sumProtein = 0;
        this.sumFat = 0;
        this.sumCarbohydrates = 0;
        this.proportionProtein = 0;
        this.proportionFat = 0;
        this.proportionCarbohydrates = 0;
        this.quantity = 0;
    }

    public String getSumKcal() {
        return String.format("%.2f", sumKcal);
    }

    public String getSumProtein() {
        return String.format("%.2f", sumProtein);
    }

    public String getSumFat() {
        return String.format("%.2f", sumFat);
    }

    public String getSumCarbohydrates() {
        return String.format("%.2f", sumCarbohydrates);
    }

    public String getProportionProtein() {
        return String.format("%.2f", proportionProtein);
    }

    public String getProportionFat() {
        return String.format("%.2f", proportionFat);
    }

    public String getProportionCarbohydrates() {
        return String.format("%.2f", proportionCarbohydrates);
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
