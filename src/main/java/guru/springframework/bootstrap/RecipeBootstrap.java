package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getAllRecipies());
    }

    private List<Recipe> getAllRecipies() {


        List<Recipe> recipeList = new ArrayList<>();

        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if (!teaspoonOptional.isPresent()) {
            throw new RuntimeException("Teaspoon UOM Not Found");
        }

        Optional<UnitOfMeasure> tablespoonOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if (!tablespoonOptional.isPresent()) {
            throw new RuntimeException("Tablespoon UOM Not Found");
        }
        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("Cup");

        if (!cupOptional.isPresent()) {
            throw new RuntimeException("Cup UOM Not Found");
        }
        Optional<UnitOfMeasure> pinchOptional = unitOfMeasureRepository.findByDescription("Pinch");

        if (!pinchOptional.isPresent()) {
            throw new RuntimeException("Pinch UOM Not Found");
        }
        Optional<UnitOfMeasure> ounceOptional = unitOfMeasureRepository.findByDescription("Ounce");

        if (!ounceOptional.isPresent()) {
            throw new RuntimeException("Ounce UOM Not Found");
        }

        UnitOfMeasure teaspoon = teaspoonOptional.get();
        UnitOfMeasure tablespoon = tablespoonOptional.get();
        UnitOfMeasure cup = cupOptional.get();
        UnitOfMeasure pinch = pinchOptional.get();
        UnitOfMeasure ounce = ounceOptional.get();

        Optional<Category> russianCoffeeOptional = categoryRepository.findByDescription("White Russian");

        if (!russianCoffeeOptional.isPresent()) {
            throw new RuntimeException("White Russian Category Not Found");
        }

        Optional<Category> irishCoffeeOptional = categoryRepository.findByDescription("Irish");

        if (!irishCoffeeOptional.isPresent()) {
            throw new RuntimeException("Irish Category Not Found");
        }

        Category whiteRussian = russianCoffeeOptional.get();
        Category irish = irishCoffeeOptional.get();

        Recipe whiteRussianCoffeeRecipe = new Recipe();
        whiteRussianCoffeeRecipe.setDescription("White Russian cocktail made with vodka, coffee liqueur," +
                " and coconut milk. Vegan and dairy-free so that all your guests can have a glass!");
        whiteRussianCoffeeRecipe.setPrepTime(0);
        whiteRussianCoffeeRecipe.setCookTime(0);
        whiteRussianCoffeeRecipe.setDifficulty(Difficulty.MODERATE);
        whiteRussianCoffeeRecipe.setDirections("Fill a rocks glass with ice. Add the vodka and coffee liqueur and give it a " +
                "quick swirl or two to mix. Top with a generous splash of coconut milk and serve. Stir together if you desire.");
        Notes russianNotes = new Notes();
        russianNotes.setRecipeNotes("Please, please, please don’t use cheap vodka for this. It’s the main ingredient and" +
                " you can’t make a good cocktail with bad booze. I highly recommend Tito’s Vodka as its widely available," +
                " affordable, and smooth.");
        russianNotes.setRecipe(whiteRussianCoffeeRecipe);
        whiteRussianCoffeeRecipe.setNotes(russianNotes);

        whiteRussianCoffeeRecipe.getIngredients().add(new Ingredient("vodka", BigDecimal.valueOf(2),
                ounce, whiteRussianCoffeeRecipe));
        whiteRussianCoffeeRecipe.getIngredients().add(new Ingredient("coffee", BigDecimal.valueOf(1),
                ounce, whiteRussianCoffeeRecipe));
        whiteRussianCoffeeRecipe.getIngredients().add(new Ingredient("Coconut Milk", BigDecimal.valueOf(1),
                tablespoon, whiteRussianCoffeeRecipe));

        recipeList.add(whiteRussianCoffeeRecipe);


        return recipeList;
    }
}
