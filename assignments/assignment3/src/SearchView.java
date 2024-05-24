import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;

public class SearchView {
    private DrinkType drinkType;
    private final Menu menu;

    private final CardLayout cardLayout = new CardLayout();

    private final String COFFEE_PANEL = "Coffee";
    private final String TEA_PANEL = "Tea";
    private final String IMAGE_PANEL = "Image";

    private JPanel typeOfDreamDrinkSpecificCriteriaPanel;
    private JPanel extrasPanel;

    // Generic fields
    private Set<MilkOptions> availableMilkOptions;
    private Set<String> availableExtras;

    // Tea fields
    private Set<Temperature> availableTemperatures;


    // user choices
    private int userMinPrice;
    private int userMaxPrice;
    private MilkOptions userMilkOption;
    private String userSugar;
    private Set<String> userExtras;
    private int userNumOfShots;
    private Temperature userTemperature;
    private int userSteepTime;

    public SearchView(Menu menu) {
        this.menu = menu;
        this.availableExtras = new HashSet<>();
        this.availableMilkOptions = new HashSet<>();
        this.availableTemperatures = new HashSet<>();
        this.userExtras = new HashSet<>();
    }

    public JPanel generateSearchView() {
        JPanel criteria = new JPanel();
        criteria.setLayout(new BoxLayout(criteria, BoxLayout.Y_AXIS));

        JPanel type = this.userInputDrinkType();
        type.setAlignmentX(0);
        criteria.add(type);
        JPanel generic = this.userInputGenericCriteria();
        generic.setAlignmentX(0);
        criteria.add(generic);

        criteria.add(Box.createRigidArea(new Dimension(0, 20)));

        this.typeOfDreamDrinkSpecificCriteriaPanel = new JPanel(cardLayout);
        this.typeOfDreamDrinkSpecificCriteriaPanel.setAlignmentX(0);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.generateImagePanel(), IMAGE_PANEL);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.userInputCoffeeCriteria(), COFFEE_PANEL);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.userInputTeaCriteria(), TEA_PANEL);

        criteria.add(this.typeOfDreamDrinkSpecificCriteriaPanel);
        return criteria;
    }

    public JPanel userInputDrinkType() {
        JPanel drinkTypePanel = new JPanel();
        drinkTypePanel.setLayout(new BoxLayout(drinkTypePanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel drinkTypeLabel = new JLabel("Drink Type:");
        innerPanel.add(drinkTypeLabel);

        JComboBox<DrinkType> drinkTypeComboBox = new JComboBox<>(DrinkType.values());
        drinkTypeComboBox.setPreferredSize(new Dimension(150, 25)); // Set preferred size
        drinkTypeComboBox.requestFocusInWindow();
        drinkTypeComboBox.setSelectedItem(DrinkType.SELECT_DRINK_TYPE);
        drinkType = (DrinkType) drinkTypeComboBox.getSelectedItem();

        drinkTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ifTypeSelected(drinkTypeComboBox);
                this.refreshExtrasPanel();
            }
        });

        innerPanel.add(drinkTypeComboBox);
        drinkTypePanel.add(innerPanel);

        return drinkTypePanel;
    }


    public void ifTypeSelected(JComboBox<DrinkType> drinkTypeComboBox) {
        this.drinkType = (DrinkType) drinkTypeComboBox.getSelectedItem();
        availableExtras = menu.findExtras(drinkType);

        assert this.drinkType != null;
        if (this.drinkType == DrinkType.COFFEE) {
            cardLayout.show(this.typeOfDreamDrinkSpecificCriteriaPanel, COFFEE_PANEL);
        } else if (this.drinkType == DrinkType.TEA) {
            cardLayout.show(this.typeOfDreamDrinkSpecificCriteriaPanel, TEA_PANEL);
        } else if (this.drinkType == DrinkType.SELECT_DRINK_TYPE) {
            cardLayout.show(this.typeOfDreamDrinkSpecificCriteriaPanel, IMAGE_PANEL);
        }
    }

    /*--------------Generic Criteria--------------*/

    public JPanel userInputGenericCriteria() {
        JPanel genericCriteriaPanel = new JPanel();
        genericCriteriaPanel.setLayout(new BoxLayout(genericCriteriaPanel, BoxLayout.Y_AXIS));

        genericCriteriaPanel.add(this.getPriceRange());
        genericCriteriaPanel.add(this.getMilkOptions());
        genericCriteriaPanel.add(this.getSugar());

        // extras panel special stuff
        this.extrasPanel = new JPanel();
        this.extrasPanel.setLayout(new BoxLayout(this.extrasPanel, BoxLayout.Y_AXIS));
        JLabel extrasLabel = new JLabel("Extras:");
        this.extrasPanel.add(extrasLabel);

        genericCriteriaPanel.add(this.extrasPanel);

        return genericCriteriaPanel;
    }

    public JPanel getPriceRange() {
        JPanel priceRangePanel = new JPanel();
        priceRangePanel.setLayout(new BoxLayout(priceRangePanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel priceRangeLabel = new JLabel("Price Range:");
        innerPanel.add(priceRangeLabel);

        JTextField minPriceField = new JTextField();
        minPriceField.setPreferredSize(new Dimension(50, 25)); // Set preferred size to control height
        minPriceField.setColumns(10); // Set columns to make it expand
        minPriceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userMinPrice = Integer.parseInt(minPriceField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                userMinPrice = Integer.parseInt(minPriceField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                userMinPrice = Integer.parseInt(minPriceField.getText());
            }
        });
        innerPanel.add(minPriceField);

        innerPanel.add(new JLabel(" to "));

        JTextField maxPriceField = new JTextField();
        maxPriceField.setPreferredSize(new Dimension(50, 25)); // Set preferred size to control height
        maxPriceField.setColumns(10); // Set columns to make it expand
        maxPriceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                userMaxPrice = Integer.parseInt(maxPriceField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                userMaxPrice = Integer.parseInt(maxPriceField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                userMaxPrice = Integer.parseInt(maxPriceField.getText());
            }
        });
        innerPanel.add(maxPriceField);

        priceRangePanel.add(innerPanel);

        return priceRangePanel;
    }


    public JPanel getMilkOptions() {
        JPanel milkOptionsPanel = new JPanel();
        milkOptionsPanel.setLayout(new BoxLayout(milkOptionsPanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel milkOptionsLabel = new JLabel("Milk Options:");
        innerPanel.add(milkOptionsLabel);

        JComboBox<MilkOptions> milkOptionsComboBox = new JComboBox<>(MilkOptions.values());
        milkOptionsComboBox.setSelectedItem(MilkOptions.FULL_CREAM);
        this.userMilkOption = (MilkOptions) milkOptionsComboBox.getSelectedItem();
        milkOptionsComboBox.setPreferredSize(new Dimension(150, 25)); // Set preferred size
        milkOptionsComboBox.addActionListener(e -> this.userMilkOption = (MilkOptions) milkOptionsComboBox.getSelectedItem());
        innerPanel.add(milkOptionsComboBox);

        milkOptionsPanel.add(innerPanel);

        return milkOptionsPanel;
    }


    public JPanel getSugar() {
        JPanel sugarPanel = new JPanel();
        sugarPanel.setLayout(new BoxLayout(sugarPanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Use FlowLayout for single line layout

        JLabel sugarLabel = new JLabel("Sugar:");
        innerPanel.add(sugarLabel);

        String[] sugarOptions = {"Yes", "No", "Skip"};
        this.userSugar = sugarOptions[0]; // Default to "Yes"
        JComboBox<String> sugarComboBox = new JComboBox<>(sugarOptions);
        sugarComboBox.setPreferredSize(new Dimension(100, 25)); // Set preferred size
        sugarComboBox.addActionListener(e -> {
            String selected = (String) sugarComboBox.getSelectedItem();
            // Set user sugar to true if "Yes" is selected, false if "No" is selected, and skip if "Skip" is selected
            this.userSugar = selected;
        });
        innerPanel.add(sugarComboBox);

        sugarPanel.add(innerPanel);

        return sugarPanel;
    }

    public void refreshExtrasPanel() {
        this.extrasPanel.removeAll();
        JLabel extrasLabel = new JLabel("Extras:");
        this.extrasPanel.add(extrasLabel);

        int rows = (int) Math.ceil((double) this.availableExtras.size() / 3);
        this.extrasPanel.setLayout(new GridLayout(rows, 3));

        for (String extra : this.availableExtras) {
            JCheckBox extraCheckBox = new JCheckBox(extra);
            extraCheckBox.addActionListener(e -> {
                if (extraCheckBox.isSelected()) {
                    this.userExtras.add(extraCheckBox.getText());
                } else {
                    this.userExtras.remove(extraCheckBox.getText());
                }
            });
            this.extrasPanel.add(extraCheckBox);
        }
        this.extrasPanel.revalidate();
        this.extrasPanel.repaint();
    }

    /*--------------Coffee Criteria--------------*/

    public JPanel userInputCoffeeCriteria() {
        JPanel coffeeCriteriaPanel = new JPanel();
        coffeeCriteriaPanel.setLayout(new BoxLayout(coffeeCriteriaPanel, BoxLayout.Y_AXIS));

        coffeeCriteriaPanel.add(this.getNumOfShots());

        return coffeeCriteriaPanel;
    }

    public JPanel getNumOfShots() {
        JPanel numOfShotsPanel = new JPanel();
        numOfShotsPanel.setLayout(new BoxLayout(numOfShotsPanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Use FlowLayout for single line layout

        JLabel numOfShotsLabel = new JLabel("Number of Shots:");
        innerPanel.add(numOfShotsLabel);

        String[] numOfShotsOptions = {"0", "1", "2", "3", "Skip"};
        JComboBox<String> numOfShotsComboBox = new JComboBox<>(numOfShotsOptions);
        numOfShotsComboBox.setPreferredSize(new Dimension(100, 25)); // Set preferred size
        numOfShotsComboBox.addActionListener(e -> {
            String selected = (String) numOfShotsComboBox.getSelectedItem();
            if ("Skip".equals(selected)) {
                this.userNumOfShots = 0;
            } else {
                this.userNumOfShots = Integer.parseInt(selected);
            }
        });
        innerPanel.add(numOfShotsComboBox);

        numOfShotsPanel.add(innerPanel);

        return numOfShotsPanel;
    }

    /*--------------Tea Criteria--------------*/

    public JPanel userInputTeaCriteria() {
        JPanel teaCriteriaPanel = new JPanel();
        teaCriteriaPanel.setLayout(new BoxLayout(teaCriteriaPanel, BoxLayout.Y_AXIS));

        teaCriteriaPanel.add(this.getTemperature());
        teaCriteriaPanel.add(this.getSteepTime());

        return teaCriteriaPanel;
    }

    public JPanel getTemperature() {
        JPanel temperaturePanel = new JPanel();
        temperaturePanel.setLayout(new BoxLayout(temperaturePanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Use FlowLayout for single line layout

        JLabel temperatureLabel = new JLabel("Temperature:");
        innerPanel.add(temperatureLabel);

        JComboBox<Temperature> temperatureComboBox = new JComboBox<>(Temperature.values());
        temperatureComboBox.setPreferredSize(new Dimension(200, 25)); // Adjust the width to fit the longest text
        temperatureComboBox.addActionListener(e -> {
            this.userTemperature = (Temperature) temperatureComboBox.getSelectedItem();
        });
        innerPanel.add(temperatureComboBox);

        temperaturePanel.add(innerPanel);

        return temperaturePanel;
    }




    public JPanel getSteepTime() {
        JPanel steepTimePanel = new JPanel();
        steepTimePanel.setLayout(new BoxLayout(steepTimePanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Use FlowLayout for single line layout

        JLabel steepTimeLabel = new JLabel("Steep Time:");
        innerPanel.add(steepTimeLabel);

        String[] steepTimeOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "Skip"};
        JComboBox<String> steepTimeComboBox = new JComboBox<>(steepTimeOptions);
        steepTimeComboBox.setPreferredSize(new Dimension(100, 25)); // Set preferred size
        steepTimeComboBox.addActionListener(e -> {
            String selected = (String) steepTimeComboBox.getSelectedItem();
            if ("Skip".equals(selected)) {
                this.userSteepTime = -1;
            } else {
                this.userSteepTime = Integer.parseInt(selected);
            }
        });
        innerPanel.add(steepTimeComboBox);

        steepTimePanel.add(innerPanel);

        return steepTimePanel;
    }





    public JPanel generateImagePanel() {

        // load images of coffee and tea
        JLabel coffeeImage = new JLabel(new ImageIcon("coffee.jpg"));
        JLabel teaImage = new JLabel(new ImageIcon("tea.jpg"));

        JPanel imagePanel = new JPanel();
        imagePanel.add(coffeeImage);
        imagePanel.add(teaImage);

        return imagePanel;
    }

    /*--------------Getters--------------*/

    public int getUserMinPrice() {
        return this.userMinPrice;
    }

    public int getUserMaxPrice() {
        return this.userMaxPrice;
    }

    public MilkOptions getUserMilkOption() {
        return this.userMilkOption;
    }

    public String getUserSugar() {
        return this.userSugar;
    }

    public Set<String> getUserExtras() {
        return this.userExtras;
    }

    public int getUserNumOfShots() {
        return this.userNumOfShots;
    }

    public Temperature getUserTemperature() {
        return this.userTemperature;
    }

    public int getUserSteepTime() {
        return this.userSteepTime;
    }

    public DrinkType getDrinkType() {
        return this.drinkType;
    }

    public Set<MilkOptions> getAvailableMilkOptions() {
        return this.availableMilkOptions;
    }

    public Set<String> getAvailableExtras() {
        return this.availableExtras;
    }

    public Set<Temperature> getAvailableTemperatures() {
        return this.availableTemperatures;
    }
}
