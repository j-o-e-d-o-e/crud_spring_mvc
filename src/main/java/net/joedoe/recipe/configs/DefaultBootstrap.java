package net.joedoe.recipe.configs;

import net.joedoe.recipe.domains.*;
import net.joedoe.recipe.services.CategoryService;
import net.joedoe.recipe.services.IRecipeService;
import net.joedoe.recipe.services.RecipeService;
import net.joedoe.recipe.services.UnitOfMeasureService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
@Profile("default")
public class DefaultBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryService categoryService;
    private final IRecipeService<Recipe> recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public DefaultBootstrap(CategoryService categoryService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @SuppressWarnings("Duplicates")
    @Override
    @Transactional //Spring treats whole method as one unit, no fetch type declaration needed
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //get uoms
        UnitOfMeasure each = unitOfMeasureService.findByDescription("Each").orElse(null);
        UnitOfMeasure tablespoon = unitOfMeasureService.findByDescription("Tablespoon").orElse(null);
        UnitOfMeasure teaspoon = unitOfMeasureService.findByDescription("Teaspoon").orElse(null);
        UnitOfMeasure dash = unitOfMeasureService.findByDescription("Dash").orElse(null);
        UnitOfMeasure pint = unitOfMeasureService.findByDescription("Pint").orElse(null);
        UnitOfMeasure cup = unitOfMeasureService.findByDescription("Cup").orElse(null);

        //guacamole recipe
        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamole.setNotes(new Notes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great."));
        guacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setServings(4);
        guacamole.setSource("Simply recipes");

        //add ingredients
        guacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), each));
        guacamole.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), each));
        guacamole.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash));
        guacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each));

        //add categories
        guacamole.addCategory(categoryService.findByDescription("American").orElse(null));
        guacamole.addCategory(categoryService.findByDescription("Mexican").orElse(null));

        recipeService.save(guacamole);

        //Tacos recipe
        Recipe tacos = new Recipe();
        tacos.setDescription("Spicy Grilled Chicken Taco");
        tacos.setCookTime(9);
        tacos.setPrepTime(20);
        tacos.setDifficulty(Difficulty.MODERATE);
        tacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n\n\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
        tacos.setNotes(new Notes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!"));
        tacos.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setServings(4);
        tacos.setSource("Simply recipes");

        //add ingredients
        tacos.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoon));
        tacos.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoon));
        tacos.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoon));
        tacos.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon));
        tacos.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        tacos.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), each));
        tacos.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoon));
        tacos.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoon));
        tacos.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoon));
        tacos.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoon));
        tacos.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), each));
        tacos.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cup));
        tacos.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), each));
        tacos.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), each));
        tacos.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pint));
        tacos.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), each));
        tacos.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), each));
        tacos.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cup));
        tacos.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), each));

        //add categories
        tacos.addCategory(categoryService.findByDescription("American").orElse(null));
        tacos.addCategory(categoryService.findByDescription("Mexican").orElse(null));

        recipeService.save(tacos);
    }
}