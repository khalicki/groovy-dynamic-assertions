package com.github.khalicki.dynamicassertions.data

class Recipe {
    String title
    List<Ingredient> ingredients

    Recipe(String title, List<Ingredient> ingredients) {
        this.title = title
        this.ingredients = ingredients
    }

    static class Ingredient {
        String name
        BigDecimal amount
        String unit

        Ingredient(String name) {
            this.name = name
        }

        Ingredient(String name, BigDecimal amount, String unit) {
            this.name = name
            this.amount = amount
            this.unit = unit
        }
    }
}
