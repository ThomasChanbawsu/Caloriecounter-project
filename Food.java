package caloriecalculator;

import java.util.ArrayList;

abstract class Food {
    protected String name;
    protected double protein;
    protected double fat;
    protected double carbs;
    protected double weight;
    static double PROTEIN_WEIGHT = 4.0;
    static double CARB_WEIGHT = 4.0;
    static double FAT_WEIGHT = 9.0;
    static double STANDARD_WEIGHT = 100.0;
    
    public Food(String name, double protein, double fat, double carbs, double weight){
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        // default weight for Food is 100g
        this.weight = weight > 0 ? weight : 100.0;
    }

    public Food(String name, ArrayList<Double> nutricList){
        this.name = name;
        this.protein = nutricList.get(0);
        this.fat = nutricList.get(1);
        this.carbs = nutricList.get(2);
        // default weight for Food is 100g
        this.weight = nutricList.get(3) > 0 ? nutricList.get(3) : 100.0;
    }

    public String get_name(){
        return name;
    }

    public void set_name(String new_name){
        name = new_name;
    }

    public double get_protein(){
        return protein;
    }

    public void set_protein(double new_protein){
        this.protein = new_protein;
    }

    public double get_fat(){
        return fat;
    }

    public void set_fat(double new_fat){
        this.fat = new_fat;
    }

    public double get_carbs(){
        return carbs;
    }

    public void set_carbs(double new_carbs){
        this.carbs = new_carbs;
    }

    public double get_weight(){
        return weight;
    }

    public void set_weight(double new_weight){
        this.weight = new_weight > 0 ? new_weight : 100;
        if (new_weight <= 0){
            System.out.println("weight entered is not valid. Setting weight to 100 g as default");
        }
    }

    public double get_calorie(){
        double calorie = protein * PROTEIN_WEIGHT + carbs * CARB_WEIGHT + fat * FAT_WEIGHT;
        return calorie * weight / STANDARD_WEIGHT;
    }
    // enter validation for serving later
    // might change this function later on
    public double get_servingsize(int servings){
        double serving_size = this.weight/servings;
        return serving_size;
    }
    // override function for all food subclasses
    public void content(){
        System.out.println("Food: " + name);
        System.out.println("Content:\nProtein: " + protein + " (g)");
        System.out.println("Fats: " + fat + " (g)");
        System.out.println("Carbs: " + carbs + " (g)");
        System.out.println("Weight: " + weight + " (g)");
        System.out.println("Total calories: " + get_calorie() + " (g)");
    }
}

//Meat, dairy, veggies, fruits, grains

class Meat extends Food{

    public Meat(String name, double protein, double fat, double carbs, double weight){
        super(name, protein, fat, carbs, weight);
    }

    public Meat(String name, ArrayList<Double> nutricList){
        super(name, nutricList);
    }
}



