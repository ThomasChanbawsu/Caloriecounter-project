package caloriecalculator;

import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {
    private String name;
    private ArrayList<Food> ingredients;
    private double totcalorie = 0.0;
    private double protein_cont = 0.0;
    private double fat_cont = 0.0;
    private double carb_cont = 0.0;
    private double weight = 0.0;

    public Recipe(String name, ArrayList<Food> ingredients){
        this.name = name;
        this.ingredients = ingredients;
        for (Food food : ingredients) {
            protein_cont += food.get_protein();
            totcalorie += food.get_calorie();
            fat_cont += food.get_fat();
            carb_cont += food.get_carbs();
            weight += food.get_weight();
        }
    }

    public Recipe(String name, Food food){
        this.name = name;
        this.ingredients = new ArrayList<Food>();
        this.ingredients.add(food);
        protein_cont = food.get_protein();
        fat_cont = food.get_fat();
        carb_cont = food.get_carbs();
        totcalorie = food.get_calorie();
    }

    public void list_ingredient(){
        for (Food food : ingredients) {
            System.out.println(food.get_name());
        }
    }

    public String get_name(){
        return name;
    }

    public double get_protein(){
        return protein_cont;
    }

    public double get_fat(){
        return fat_cont;
    }

    public double get_carbs(){
        return carb_cont;
    }

    public double get_calorie(){
        return totcalorie;
    }
    
    public double get_weight(){
        return weight;
    }

    public void ingred_nutrition(){
        for (Food food : ingredients) {
            food.content();
        }
    }

    public ArrayList<Double> get_macros(){
        ArrayList<Double> info = new ArrayList<Double>(Arrays.asList(protein_cont, fat_cont, carb_cont, totcalorie));
        return info;
    }
    
    public void add_food(Food food){
        ingredients.add(food);
        protein_cont += food.get_protein();
        fat_cont += food.get_fat();
        carb_cont += food.get_carbs();
        totcalorie += food.get_calorie();
    }

    public void add_food(ArrayList<Food> new_ingredients){
        for (Food food : new_ingredients) {
            this.add_food(food);
        }
    }

    public void get_food(String name){
        Boolean getFood = false;
        for (Food food : ingredients) {
            if (food.get_name().equalsIgnoreCase(name)) {
                food.content();
                getFood = true;
            } 
        }
        if (!getFood) {
            System.out.println("Given food is not found in recipe.");            
        }
    }

    public void remove_food(String name){
        boolean removeFood = false;
        for (Food food : ingredients) {
            if (food.get_name().equalsIgnoreCase(name)){
                ingredients.remove(food);
                removeFood = true;
            }
        }
        if (!removeFood) {
            System.out.println("Given food is not found in recipe.");
        }
    }
}
