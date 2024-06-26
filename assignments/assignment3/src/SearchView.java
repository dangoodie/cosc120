import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents the search view of the application.
 * This search view contains user inputs to search through the menu.
 * It has been modified from UNE COSC120 Lecture 10 material.
 */
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

    // Decimal filter fields
    private JTextField minPriceField;
    private JTextField maxPriceField;

    // user choices
    private double userMinPrice;
    private double userMaxPrice;
    private MilkOptions userMilkOption;
    private String userSugar;
    private Set<String> userExtras;
    private int userNumOfShots;
    private Temperature userTemperature;
    private int userSteepTime;

    /**
     * Constructor used to create a SearchView object.
     * @param menu the menu object to search through
     */
    public SearchView(Menu menu) {
        this.menu = menu;
        this.availableExtras = new HashSet<>();
        this.availableMilkOptions = new HashSet<>();
        this.availableTemperatures = new HashSet<>();
        this.userExtras = new HashSet<>();
    }

    /**
     * Generates the search view.
     * @return a JPanel containing the search view
     */
    public JPanel generateSearchView() {
        JPanel criteria = new JPanel();
        criteria.setLayout(new BoxLayout(criteria, BoxLayout.Y_AXIS));
        criteria.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Search for your dream drink!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        criteria.add(titleLabel);

        criteria.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel type = this.userInputDrinkType();
        type.setAlignmentX(Component.LEFT_ALIGNMENT);
        criteria.add(type);

        JPanel generic = this.userInputGenericCriteria();
        generic.setAlignmentX(Component.LEFT_ALIGNMENT);
        criteria.add(generic);

        criteria.add(Box.createRigidArea(new Dimension(0, 20)));

        this.typeOfDreamDrinkSpecificCriteriaPanel = new JPanel(cardLayout);
        this.typeOfDreamDrinkSpecificCriteriaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.generateImagePanel(), IMAGE_PANEL);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.userInputCoffeeCriteria(), COFFEE_PANEL);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.userInputTeaCriteria(), TEA_PANEL);

        criteria.add(this.typeOfDreamDrinkSpecificCriteriaPanel);

        criteria.add(Box.createRigidArea(new Dimension(0, 20)));
        return criteria;
    }

    /**
     * Generates the drink type panel.
     * @return a JPanel containing the drink type panel
     */
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

    /**
     * Method to show the specific drink panel based on the selected drink type.
     * @param drinkTypeComboBox the combo box containing the drink type
     */
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
    /**
     * Generates the generic criteria panel.
     * This panel contains the generic criteria that are common to all drinks.
     * It includes price range, milk options, sugar, and extras.
     * @return a JPanel containing the generic criteria panel
     */
    public JPanel userInputGenericCriteria() {
        JPanel genericCriteriaPanel = new JPanel();
        genericCriteriaPanel.setLayout(new BoxLayout(genericCriteriaPanel, BoxLayout.Y_AXIS));
        genericCriteriaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel priceRangePanel = this.getPriceRange();
        priceRangePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genericCriteriaPanel.add(priceRangePanel);

        JPanel milkOptionsPanel = this.getMilkOptions();
        milkOptionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genericCriteriaPanel.add(milkOptionsPanel);

        JPanel sugarPanel = this.getSugar();
        sugarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genericCriteriaPanel.add(sugarPanel);

        // extras panel special stuff
        this.extrasPanel = new JPanel();
        this.extrasPanel.setLayout(new BoxLayout(this.extrasPanel, BoxLayout.Y_AXIS));
        JLabel extrasLabel = new JLabel("Extras:");
        this.extrasPanel.add(extrasLabel);
        this.extrasPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        genericCriteriaPanel.add(this.extrasPanel);

        return genericCriteriaPanel;
    }

    /**
     * Generates the price range panel.
     * @return a JPanel containing the price range panel
     */
    public JPanel getPriceRange() {
        JPanel priceRangePanel = new JPanel();
        priceRangePanel.setLayout(new BoxLayout(priceRangePanel, BoxLayout.Y_AXIS));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel priceRangeLabel = new JLabel("Price Range:");
        innerPanel.add(priceRangeLabel);

        minPriceField = new JTextField();
        minPriceField.setPreferredSize(new Dimension(50, 25)); // Set preferred size to control height
        minPriceField.setColumns(10); // Set columns to make it expand

        maxPriceField = new JTextField();
        maxPriceField.setPreferredSize(new Dimension(50, 25)); // Set preferred size to control height
        maxPriceField.setColumns(10); // Set columns to make it expand

        minPriceField.getDocument().addDocumentListener(new PriceDocumentListener());
        maxPriceField.getDocument().addDocumentListener(new PriceDocumentListener());

        innerPanel.add(minPriceField);
        innerPanel.add(new JLabel(" to "));
        innerPanel.add(maxPriceField);

        priceRangePanel.add(innerPanel);

        return priceRangePanel;
    }

    private class PriceDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            validatePrices();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validatePrices();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            validatePrices();
        }

        private void validatePrices() {
            String minText = minPriceField.getText();
            String maxText = maxPriceField.getText();

            try {
                userMinPrice = minText.isEmpty() ? 0 : Double.parseDouble(minText);
                if (userMinPrice < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                userMinPrice = 0;
                minPriceField.setBackground(Color.PINK); // Indicate error
                JOptionPane.showMessageDialog(null, "Please enter a valid positive number for the minimum price.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                userMaxPrice = maxText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxText);
                if (userMaxPrice < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                userMaxPrice = Double.MAX_VALUE;
                maxPriceField.setBackground(Color.PINK); // Indicate error
                JOptionPane.showMessageDialog(null, "Please enter a valid positive number for the maximum price.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userMaxPrice < userMinPrice) {
                minPriceField.setBackground(Color.PINK); // Indicate error
                maxPriceField.setBackground(Color.PINK); // Indicate error
                JOptionPane.showMessageDialog(null, "Maximum price cannot be less than minimum price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                minPriceField.setBackground(Color.WHITE); // Reset background
                maxPriceField.setBackground(Color.WHITE); // Reset background
            }
        }
    }

    /**
     * Generates the milk options panel.
     * @return a JPanel containing the milk options panel
     */
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

    /**
     * Generates the sugar panel.
     * @return a JPanel containing the sugar panel
     */
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

    /**
     * Refreshes the extras panel with the available extras
     * based on the type of drink selected.
     */
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

    /**
     * Generates the coffee criteria panel.
     * @return a JPanel containing the coffee criteria panel
     */
    public JPanel userInputCoffeeCriteria() {
        JPanel coffeeCriteriaPanel = new JPanel();
        coffeeCriteriaPanel.setLayout(new BoxLayout(coffeeCriteriaPanel, BoxLayout.Y_AXIS));

        coffeeCriteriaPanel.add(this.getNumOfShots());

        return coffeeCriteriaPanel;
    }

    /**
     * Generates the number of shots panel.
     * @return a JPanel containing the number of shots panel
     */
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

    /**
     * Generates the tea criteria panel.
     * @return a JPanel containing the tea criteria panel
     */
    public JPanel userInputTeaCriteria() {
        JPanel teaCriteriaPanel = new JPanel();
        teaCriteriaPanel.setLayout(new BoxLayout(teaCriteriaPanel, BoxLayout.Y_AXIS));

        teaCriteriaPanel.add(this.getTemperature());
        teaCriteriaPanel.add(this.getSteepTime());

        return teaCriteriaPanel;
    }

    /**
     * Generates the temperature panel.
     * @return a JPanel containing the temperature panel
     */
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

        // set the default temperature
        this.userTemperature = (Temperature) temperatureComboBox.getSelectedItem();
        temperaturePanel.add(innerPanel);

        return temperaturePanel;
    }

    /**
     * Generates the steep time panel.
     * @return a JPanel containing the steep time panel
     */
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

    /**
     * Generates the image panel.
     * @return a JPanel containing the image panel
     */
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

    /**
     * @return the user's minimum price
     */
    public double getUserMinPrice() {
        return this.userMinPrice;
    }

    /**
     * @return the user's maximum price
     */
    public double getUserMaxPrice() {
        return this.userMaxPrice;
    }

    /**
     * @return the user's milk option
     */
    public MilkOptions getUserMilkOption() {
        return this.userMilkOption;
    }

    /**
     * @return the user's sugar choice
     */
    public String getUserSugar() {
        return this.userSugar;
    }

    /**
     * @return the user's extras
     */
    public Set<String> getUserExtras() {
        return this.userExtras;
    }

    /**
     * @return the user's number of shots
     */
    public int getUserNumOfShots() {
        return this.userNumOfShots;
    }

    /**
     * @return the user's temperature
     */
    public Temperature getUserTemperature() {
        return this.userTemperature;
    }

    /**
     * @return the user's steep time
     */
    public int getUserSteepTime() {
        return this.userSteepTime;
    }

    /**
     * @return the drink type
     */
    public DrinkType getDrinkType() {
        return this.drinkType;
    }

    /**
     * @return the available milk options
     */
    public Set<MilkOptions> getAvailableMilkOptions() {
        return this.availableMilkOptions;
    }

    /**
     * @return the available extras
     */
    public Set<String> getAvailableExtras() {
        return this.availableExtras;
    }

    /**
     * @return the available temperatures
     */
    public Set<Temperature> getAvailableTemperatures() {
        return this.availableTemperatures;
    }
}
