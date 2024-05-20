import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SearchView {
    private DrinkType drinkType;

    private final CardLayout cardLayout = new CardLayout();

    private final String COFFEE_PANEL = "Coffee";
    private final String TEA_PANEL = "Tea";

    private JPanel typeOfDreamDrinkSpecificCriteriaPanel;

    private int minPrice;
    private int maxPrice;
    private Set<MilkOptions> availableMilkOptions;
    private boolean sugar;
    private Set<String> availableExtras;

    // Coffee fields
    private int numOfShots;

    // Tea fields
    private Set<Temperature> availableTemperatures;
    private int steepTime;


    // user choices
    private int userMinPrice;
    private int userMaxPrice;
    private MilkOptions userMilkOption;
    private boolean userSugar;
    private Set<String> userExtras;
    private int userNumOfShots;
    private Temperature userTemperature;
    private int userSteepTime;

    public SearchView(Set<String> availableExtras) {
        this.availableExtras = availableExtras;
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
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.userInputCoffeeCriteria(), COFFEE_PANEL);
        this.typeOfDreamDrinkSpecificCriteriaPanel.add(this.userInputTeaCriteria(), TEA_PANEL);

        criteria.add(this.typeOfDreamDrinkSpecificCriteriaPanel);
        return criteria;
    }

    public JPanel userInputDrinkType() {
        JPanel drinkTypePanel = new JPanel();
        drinkTypePanel.setLayout(new BoxLayout(drinkTypePanel, BoxLayout.Y_AXIS));
        JLabel drinkTypeLabel = new JLabel("Drink Type:");
        drinkTypePanel.add(drinkTypeLabel);

        JComboBox<DrinkType> drinkTypeComboBox = new JComboBox<>(DrinkType.values());
        drinkTypeComboBox.addActionListener(e -> ifTypeSelected(drinkTypeComboBox));
        drinkTypePanel.add(drinkTypeComboBox);

        return drinkTypePanel;
    }

    public void ifTypeSelected(JComboBox<DrinkType> drinkTypeComboBox) {
        this.drinkType = (DrinkType) drinkTypeComboBox.getSelectedItem();
        assert this.drinkType != null;
        this.cardLayout.show(this.typeOfDreamDrinkSpecificCriteriaPanel, this.drinkType == DrinkType.COFFEE ? COFFEE_PANEL : TEA_PANEL);
    }

    /*--------------Generic Criteria--------------*/

    public JPanel userInputGenericCriteria() {
        JPanel genericCriteriaPanel = new JPanel();
        genericCriteriaPanel.setLayout(new BoxLayout(genericCriteriaPanel, BoxLayout.Y_AXIS));

        genericCriteriaPanel.add(this.getPriceRange());
        genericCriteriaPanel.add(this.getMilkOptions());
        genericCriteriaPanel.add(this.getSugar());
        genericCriteriaPanel.add(this.getExtras());

        return genericCriteriaPanel;
    }

    public JPanel getPriceRange() {
        JPanel priceRangePanel = new JPanel();
        priceRangePanel.setLayout(new BoxLayout(priceRangePanel, BoxLayout.Y_AXIS));
        JLabel priceRangeLabel = new JLabel("Price Range:");
        priceRangePanel.add(priceRangeLabel);

        JPanel priceRangeInput = new JPanel();
        priceRangeInput.setLayout(new BoxLayout(priceRangeInput, BoxLayout.X_AXIS));

        JTextField minPriceField = new JTextField();
        minPriceField.addActionListener(e -> this.userMinPrice = Integer.parseInt(minPriceField.getText()));
        priceRangeInput.add(minPriceField);

        priceRangeInput.add(new JLabel(" to "));

        JTextField maxPriceField = new JTextField();
        maxPriceField.addActionListener(e -> this.userMaxPrice = Integer.parseInt(maxPriceField.getText()));
        priceRangeInput.add(maxPriceField);

        priceRangePanel.add(priceRangeInput);

        return priceRangePanel;
    }

    public JPanel getMilkOptions() {
        JPanel milkOptionsPanel = new JPanel();
        milkOptionsPanel.setLayout(new BoxLayout(milkOptionsPanel, BoxLayout.Y_AXIS));
        JLabel milkOptionsLabel = new JLabel("Milk Options:");
        milkOptionsPanel.add(milkOptionsLabel);

        JComboBox<MilkOptions> milkOptionsComboBox = new JComboBox<>(MilkOptions.values());
        milkOptionsComboBox.addActionListener(e -> this.userMilkOption = (MilkOptions) milkOptionsComboBox.getSelectedItem());
        milkOptionsPanel.add(milkOptionsComboBox);

        return milkOptionsPanel;
    }

    public JPanel getSugar() {
        JPanel sugarPanel = new JPanel();
        sugarPanel.setLayout(new BoxLayout(sugarPanel, BoxLayout.Y_AXIS));
        JLabel sugarLabel = new JLabel("Sugar:");
        sugarPanel.add(sugarLabel);

        JCheckBox sugarCheckBox = new JCheckBox();
        sugarCheckBox.addActionListener(e -> this.userSugar = sugarCheckBox.isSelected());
        sugarPanel.add(sugarCheckBox);

        return sugarPanel;
    }

    public JPanel getExtras() {
        JPanel extrasPanel = new JPanel();
        extrasPanel.setLayout(new BoxLayout(extrasPanel, BoxLayout.Y_AXIS));
        JLabel extrasLabel = new JLabel("Extras:");
        extrasPanel.add(extrasLabel);

        for (String extra : this.availableExtras) {
            JCheckBox extraCheckBox = new JCheckBox(extra);
            extraCheckBox.addActionListener(e -> {
                if (extraCheckBox.isSelected()) {
                    this.userExtras.add(extraCheckBox.getText());
                } else {
                    this.userExtras.remove(extraCheckBox.getText());
                }
            });
            extrasPanel.add(extraCheckBox);
        }

        return extrasPanel;
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
        JLabel numOfShotsLabel = new JLabel("Number of Shots:");
        numOfShotsPanel.add(numOfShotsLabel);

        JTextField numOfShotsField = new JTextField();
        numOfShotsField.addActionListener(e -> this.userNumOfShots = Integer.parseInt(numOfShotsField.getText()));
        numOfShotsPanel.add(numOfShotsField);

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
        JLabel temperatureLabel = new JLabel("Temperature:");
        temperaturePanel.add(temperatureLabel);

        JComboBox<Temperature> temperatureComboBox = new JComboBox<>(Temperature.values());
        temperatureComboBox.addActionListener(e -> this.userTemperature = (Temperature) temperatureComboBox.getSelectedItem());
        temperaturePanel.add(temperatureComboBox);

        return temperaturePanel;
    }

    public JPanel getSteepTime() {
        JPanel steepTimePanel = new JPanel();
        steepTimePanel.setLayout(new BoxLayout(steepTimePanel, BoxLayout.Y_AXIS));
        JLabel steepTimeLabel = new JLabel("Steep Time:");
        steepTimePanel.add(steepTimeLabel);

        JTextField steepTimeField = new JTextField();
        steepTimeField.addActionListener(e -> this.userSteepTime = Integer.parseInt(steepTimeField.getText()));
        steepTimePanel.add(steepTimeField);

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

    public boolean getUserSugar() {
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

    public int getMinPrice() {
        return this.minPrice;
    }

    public int getMaxPrice() {
        return this.maxPrice;
    }


}
