package caloriecalculator;
import java.util.ArrayList;
import java.util.Arrays;

class Main{
    public static void main(String[] args){
        //main code
        //EnterRecipeBox mybox = new EnterRecipeBox(); 
        
        //EnterIngredientBox box = new EnterIngredientBox();

        // temp code for testing the recipebox layout
        ArrayList<Double> pseudo_data = new ArrayList<Double>(Arrays.asList(45.0,20.0,70.0,2000.0));
        Food squid = new Meat("squid", pseudo_data);
        Recipe calamari = new Recipe("fried calamari", squid);
        ResultBox result = new ResultBox(calamari);
    }
}
