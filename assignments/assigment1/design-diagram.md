# Design Diagram
```plantuml
@startuml
class Coffee {
    - Int id
    - String name
    - String price
    - String minPrice
    - String maxPrice
    - Int numberOfShots
    - boolean sugar
    - Set<String> milkOptions
    - Set<String> extras
    - String description
    + getId(): Int
    + getName(): String
    + getPrice(): String
    + getNumberOfShots(): Int
    + hasSugar(): boolean
    + getMilkOptions(): Set<String>
    + getExtras(): Set<String>
    + getDescription(): String
    + getMinPrice(): String
    + getMaxPrice(): String
    + isInPriceRange(String, String): boolean
}

class Geek {
    - String name
    - String phoneNumber
    + getName(): String
    + getPhoneNumber(): String
}

class Order {
    - Person person
    - Coffee coffeeOrder
    - String name
    - String orderNumber
    - String milkSelection
    - String extrasSelection
    + selectMilkOption(Set<String> milkOptions): String
    + selectExtras(Set<String> extras): String
    + getName(): String
    + getOrderNumber(): String
    + getMilkSelection(): String
    + getExtrasSelection(): String    
}

class Menu {
    - Set<Coffee> allCoffees
    + getAllCoffees(): Set<Coffee>
    + getCoffeeById(Int id): Coffee
    + findDreamCoffee(Coffee): Set<Coffee>
}

class MenuSearcher {
    - Menu menu
    + loadMenuFromFile(String): void
    + userInputSelection(): Coffee
    + searchMenu(Coffee): Set<Coffee>
    + getUserInfo(): Person
    + writeOrderToFile(Order): void
    + main(String[]): void
}

```