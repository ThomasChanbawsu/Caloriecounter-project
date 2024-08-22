package caloriecalculator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class UIBox extends JFrame implements ActionListener{
    JFrame frame;
    JButton cancel_button;
    JButton proceed_button;
    protected JPanel bottom_panel;
    static String recipe_name;

    protected UIBox(){
        // create panels and buttons
        cancel_button = new JButton();
        proceed_button = new JButton();
        bottom_panel = new JPanel();

        cancel_button.setSize(100, 50);
        cancel_button.setText("Cancel");
        //cancel_button.add(new JLabel("Cancel"));
        cancel_button.addActionListener(this);
        proceed_button.setSize(100, 50);
        proceed_button.setText("Proceed");
        //proceed_button.add(new JLabel("Proceed"));
        proceed_button.addActionListener(this);

        bottom_panel.setBounds(0, 400, 400, 150);
        bottom_panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        bottom_panel.setBackground(Color.ORANGE);
        bottom_panel.add(cancel_button);
        bottom_panel.add(proceed_button);
        
        // basic settings for frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,420);
        this.setLayout(new BorderLayout());
        this.setTitle("Recipe Calorie counter");
        // be wary of adding txtfields with different color as this appears to mess up the background color somehow
        //this.getContentPane().setBackground(Color.ORANGE);
        this.add(bottom_panel, BorderLayout.SOUTH);
        
    }

    @Override
    public void actionPerformed(ActionEvent e){}
}

class EnterRecipeBox extends UIBox{
    
    private JTextField txtbox;
    EnterRecipeBox(){
        super();
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 300, 100);
        txtbox = new JTextField();
        txtbox.setPreferredSize(new Dimension(250, 40));
        txtbox.setOpaque(true);
        panel.add(new JLabel("Enter recipe name:"));
        panel.add(txtbox);
        this.add(panel, BorderLayout.CENTER);
        // always ensure these lines are put after adding everything
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == proceed_button) {
            if (!check_emptyboxes()) {
            this.dispose();
            recipe_name = txtbox.getText();
            EnterIngredientBox ingred_box = new EnterIngredientBox();
        }
        } if (e.getSource() == cancel_button) {
            this.dispose();
        }
    }

    public boolean check_emptyboxes(){
        if (txtbox.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Recipe name is left empty! Please enter name.", 
            "Empty input", JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }
}

class EnterIngredientBox extends UIBox{
    // change JTextField into arraylist
    private ArrayList<JTextField> txtboxes = new ArrayList<JTextField>();
    private JButton addmore_button;
    private ArrayList<Food> ingredients = new ArrayList<Food>();
    private int ingredient_counter = 0;

    EnterIngredientBox(){
        super();
        // edit: change name panel into more of a title panel
        JPanel title_panel = new JPanel();
        title_panel.setPreferredSize(new Dimension(400, 70));
        title_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        title_panel.add(new JLabel("Enter all the ingredients used in the recipe."));
        
        // adding content panel
        JPanel content_panel = new JPanel();
        content_panel.setLayout(new BoxLayout(content_panel, BoxLayout.Y_AXIS));
        add_txtboxes(content_panel);

        // editing the bottom panel
        addmore_button = new JButton();
        addmore_button.setSize(100, 50);
        addmore_button.setBackground(Color.LIGHT_GRAY);
        addmore_button.add(new JLabel("Add ingredient"));
        addmore_button.addActionListener(this);
        addmore_button.setEnabled(false);

        bottom_panel.add(addmore_button);

        this.add(title_panel, BorderLayout.NORTH);
        this.add(content_panel, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public void add_txtboxes(JPanel panel){
        ArrayList<String> INFO = new ArrayList<String>(Arrays.asList("Ingredient", "Protein", "Fat", "Carbs", "Weight"));
        String formattedString;
        for (int i = 0; i < INFO.size(); i++) {
            JTextField txtbox = new JTextField();
            switch (INFO.get(i)) {
                case ("Ingredient"):
                    formattedString = String.format("%s: ", INFO.get(i));
                    break;

                case ("Weight"):
                    formattedString = String.format("%s (g): ", INFO.get(i));
                    break;
            
                default:
                    formattedString = String.format("%s (/100g): ", INFO.get(i));
                    break;
            }
            txtbox.setPreferredSize(new Dimension(70, 40));
            txtbox.setOpaque(true);
            txtbox.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e){
                    checktxtboxes();
                }
                public void changedUpdate(DocumentEvent e){
                    checktxtboxes();
                }
                public void removeUpdate(DocumentEvent e){
                    checktxtboxes();
                }
                private void checktxtboxes(){
                    if (!txtboxes.stream().anyMatch(n -> n.getText().isEmpty())) {
                        addmore_button.setEnabled(true);
                        addmore_button.setBackground(Color.GREEN);
                    } else {
                        addmore_button.setEnabled(false);
                        addmore_button.setBackground(Color.LIGHT_GRAY);
                    }
                }
            });
            panel.add(new JLabel(formattedString));
            panel.add(txtbox);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            txtboxes.add(txtbox);
        }
    }

    public void enter_ingredient(){
        String name = "";
        ArrayList<Double> data = new ArrayList<Double>();
        try {
       for (int i = 0; i < txtboxes.size(); i++) {
            switch (i) {
                case 0:
                    name = txtboxes.get(i).getText();
                    break;
            
                default:
                    
                        data.add(Double.parseDouble(txtboxes.get(i).getText()));
                    
                    
                    break;
            }
            txtboxes.get(i).setText("");
       }
       ingredients.add(new Meat(name, data));
       ingredient_counter += 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input detected. Please enter again.");
            for (JTextField text : txtboxes) {
                text.setText("");
            }                
        }
    }

    private boolean checkenteronce(){
        if (ingredient_counter == 0){
            JOptionPane.showMessageDialog(null, "You must enter at least one ingredient before proceeding.", 
            "Empty Recipe Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // can't use switch on value of object type
        if (e.getSource() == cancel_button) {
            this.dispose();
        }
        if (e.getSource() == addmore_button) {
            //ArrayList<JTextField> txtlist = new ArrayList<JTextField>(Arrays.asList(name_box, protein_box, fat_box, carbs_box)); 
            // will need to add tests/functions for validating and checking
            enter_ingredient();
        }
        if (e.getSource() == proceed_button) {
            if (checkenteronce()) {
                // create box showing result if everything is fine
                Recipe user_Recipe = new Recipe(recipe_name, ingredients);
                this.dispose();
                ResultBox results = new ResultBox(user_Recipe);
            }
        }
    }

    
}

class ResultBox extends UIBox{
    // no cancel button
    // change label of proceed button to return
    private JPanel result_panel, list_panel;
    private ArrayList<String> RESULT_LABEL = new ArrayList<String>(Arrays.asList("Protein", "Fat", "Carbs", "Calories"));
    private JTabbedPane tabbedPane = new JTabbedPane();
    //private JMenuBar menubar = new JMenuBar();
    // change parameter to Recipe object later
    ResultBox(Recipe user_recipe){
        
        // JMenu calorinfo_menu = new JMenu("Stats");
        // JMenu ingred_menu = new JMenu("Ingredients");
        title();
        result_panel = new JPanel();
        result_panel.setLayout(new BoxLayout(result_panel, BoxLayout.Y_AXIS));
        add_recipename(result_panel, user_recipe.get_name());
        add_nutrients(result_panel, user_recipe.get_macros());
        add_weight(result_panel, user_recipe.get_weight());

        list_panel = new JPanel();
        list_panel.setLayout(new BoxLayout(list_panel, BoxLayout.Y_AXIS));
        create_list(list_panel, user_recipe.get_ingredients());


        cancel_button.setText("Close");
        proceed_button.setText("Continue");
        //this.add(result_panel, BorderLayout.CENTER);

        // adding panels to the tabbedpane
        tabbedPane.addTab("Stats", result_panel);
        tabbedPane.addTab("Ingredients", list_panel);
        this.add(tabbedPane, BorderLayout.CENTER);
        //this.pack();
        // menubar.add(calorinfo_menu);
        // menubar.add(ingred_menu);
        // this.setJMenuBar(menubar);
        this.setVisible(true);
    }

    public void title(){
        JPanel title = new JPanel();
        JLabel macros_info = new JLabel("Macros Info");
        macros_info.setFont(new Font("Serif", Font.PLAIN, 25));
        title.add(macros_info);
        this.add(title, BorderLayout.NORTH);
    }

    public void add_recipename(JPanel panel, String recipe_name){
        String formatString = String.format("Recipe name: %s", recipe_name);
        JLabel label = new JLabel(formatString);
        label.setFont(new Font("Helvetica", Font.PLAIN, 16));
        result_panel.add(label);
        result_panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public void add_nutrients(JPanel panel, ArrayList<Double> nutrit_results){
        String formattedString;
        for (int i = 0; i < nutrit_results.size(); i++) {
            JLabel label = new JLabel();
            if (RESULT_LABEL.get(i) == "Calories") {
                formattedString = String.format("%s : %.2f (cal)", RESULT_LABEL.get(i), nutrit_results.get(i));
            } else {
            formattedString = String.format("%s : %.2f (g)", RESULT_LABEL.get(i), nutrit_results.get(i));
            }
            label.setText(formattedString);
            Font customFont = new Font("Helvetica", Font.PLAIN, 16);
            label.setFont(customFont);
            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }    
    }

    public void add_weight(JPanel panel, double weight){
        JLabel label = new JLabel(String.format("Weight: %.2f (g)", weight));
        label.setFont(new Font("Helvetica", Font.PLAIN, 16));
        panel.add(label);
    }

    public void create_list(JPanel panel, ArrayList<Food> ingredients){
        for (Food item : ingredients) {
            JLabel label = new JLabel();
            String formattedString = String.format("- %s (%.2f g)", item.get_name(), item.get_weight());
            label.setText(formattedString);
            label.setFont(new Font("Helvetica", Font.PLAIN, 16));
            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == cancel_button) {
            this.dispose();
        }
        if (e.getSource() == proceed_button) {
            this.dispose();
            EnterRecipeBox gui_box = new EnterRecipeBox();
        }
    }

    // Add a ChangeListener to the JTabbedPane
    

    
}