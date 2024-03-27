/**
 * @author Daniel Gooden (dgooden@une.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

import java.util.Set;

/**
 * A class that represents a coffee object.
 */

public class Coffee {
    private final int id;
    private final String name;
    private final Double price;
    private Double minPrice;
    private Double maxPrice;
    private final int numberOfShots;
    private final boolean sugar;
    private final Set<MilkOptions> milkOptions;
    private final Set<Extras> extras;
    private MilkOptions selectedMilkOption;
    private Set<Extras> selectedExtras;
    private final String description;

    /**
     * Constructor used to create a Coffee object
     * @param id an int value representing the coffee's unique identifier
     * @param name a String value representing the coffee's name
     * @param price a Double value representing the coffee's price
     * @param numberOfShots an int value representing the number of shots in the coffee
     * @param sugar a boolean value representing whether the coffee has sugar
     * @param milkOptions a Set of MilkOptions representing the milk options available for the coffee
     * @param extras a Set of Extras representing the extras available for the coffee
     * @param description a String representing a description of the coffee
     */

    public Coffee(int id, String name, Double price, int numberOfShots, boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfShots = numberOfShots;
        this.sugar = sugar;
        this.milkOptions = milkOptions;
        this.extras = extras;
        this.description = description;
    }
    //getters
    /**
     * id is unique to the Coffee object
     * @return the Coffee's id
     */
    public int getId() {
        return id;
    }

    /**
     * name of the Coffee
     * @return the Coffee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Double representing the price of the Coffee
     * @return the Coffee's price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Double representing the minimum price of the Coffee
     * used to build the Dream Coffee order
     * @return the Coffee's minimum price
     */
    public Double getMinPrice() {
        return minPrice;
    }

    /**
     * Double representing the maximum price of the Coffee
     * used to build the Dream Coffee order
     * @return the Coffee's maximum price
     */
    public Double getMaxPrice() {
        return maxPrice;
    }

    /**
     * int representing the number of shots in the Coffee
     * @return the Coffee's number of shots
     */
    public int getNumberOfShots() {
        return numberOfShots;
    }

    /**
     * boolean representing whether the Coffee has sugar
     * @return the Coffee's sugar status
     */
    public boolean hasSugar() {
        return sugar;
    }

    /**
     * Set of MilkOptions representing the milk options available for the Coffee
     * @return the Coffee's milk options
     */
    public Set<MilkOptions> getMilkOptions() {
        return milkOptions;
    }

    /**
     * Set of Extras representing the extras available for the Coffee
     * @return the Coffee's extras
     */
    public Set<Extras> getExtras() {
        return extras;
    }

    /**
     * String representing a description of the Coffee
     * @return the Coffee's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * MilkOptions representing the selected milk option for the Coffee
     * used to build the final coffee order
     * @return the Coffee's selected milk option
     */
    public MilkOptions getSelectedMilkOption() {
        return selectedMilkOption;
    }

    /**
     * Set of Extras representing the selected extras for the Coffee
     * used to build the final coffee order
     * @return the Coffee's selected extras
     */
    public Set<Extras> getSelectedExtras() {
        return selectedExtras;
    }

    //setters
    /**
     * Setter for the minimum price of a Coffee
     * used to build the Dream Coffee order
     * @param minPrice the minimum price of the Coffee
     */
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * Setter for the maximum price of a Coffee
     * used to build the Dream Coffee order
     * @param maxPrice the maximum price of the Coffee
     */
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Setter for the selected milk option of a Coffee
     * used to build the final coffee order
     * @param selectedMilkOption the selected milk option of the Coffee
     */
    public void setSelectedMilkOption(MilkOptions selectedMilkOption) {
        this.selectedMilkOption = selectedMilkOption;
    }

    /**
     * Setter for the selected extras of a Coffee
     * used to build the final coffee order
     * @param selectedExtras the selected extras of the Coffee
     */
    public void setSelectedExtras(Set<Extras> selectedExtras) {
        this.selectedExtras = selectedExtras;
    }

    //methods
    /**
     * Method used to determine if a Coffee is in a price range
     * @param minPrice a Double representing the minimum price of the range
     * @param maxPrice a Double representing the maximum price of the range
     * @return a boolean value representing whether the Coffee is in the price range
     */
    public boolean isInPriceRange(Double minPrice, Double maxPrice) {
        return this.price >= minPrice && this.price <= maxPrice;
    }

    /**
     * Method used to determine if a Coffee has a specific extra
     * This method returns true if the Coffee has ANY of the extras in the Set
     * @param extras a Set of Extras representing the extras to check for
     * @return a boolean value representing whether the Coffee has the extra
     */
    public boolean hasExtra(Set<Extras> extras) {
        for (Extras extra : extras) {
            if (this.extras.contains(extra)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to determine if a Coffee has a specific milk option
     * This method returns true if the Coffee has ANY of the milk options in the Set
     * @param milkOptions a Set of MilkOptions representing the milk options to check for
     * @return a boolean value representing whether the Coffee has the milk option
     */
    public boolean hasMilkOption(Set<MilkOptions> milkOptions) {
        for (MilkOptions milkOption : milkOptions) {
            if (this.milkOptions.contains(milkOption)) {
                return true;
            }
        }
        return false;
    }

}
