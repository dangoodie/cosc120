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

    public SearchView(Set<String> availableExtras, Set<MilkOptions> availableMilkOptions, Set<Temperature> availableTemperatures) {
        this.availableExtras = availableExtras;
        this.availableMilkOptions = availableMilkOptions;
        this.availableTemperatures = availableTemperatures;

        this.userExtras = new HashSet<>();
    }

}
