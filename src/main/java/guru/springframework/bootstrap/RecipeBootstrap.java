package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
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
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getAllRecipies());
        log.debug("Loading Bootstrap Data");
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

        //Recipe 1
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
        Set<Category> categorySet = new HashSet<>();
        categorySet.add(whiteRussian);
        whiteRussianCoffeeRecipe.setCategories(categorySet);
        whiteRussianCoffeeRecipe.addIngredient(new Ingredient("vodka", BigDecimal.valueOf(2),
                ounce, whiteRussianCoffeeRecipe));
        whiteRussianCoffeeRecipe.addIngredient(new Ingredient("coffee", BigDecimal.valueOf(1),
                ounce, whiteRussianCoffeeRecipe));
        whiteRussianCoffeeRecipe.addIngredient(new Ingredient("Coconut Milk", BigDecimal.valueOf(1),
                tablespoon, whiteRussianCoffeeRecipe));

        recipeList.add(whiteRussianCoffeeRecipe);

        //Recipe 2

        Recipe dummyRecipe = new Recipe();

        dummyRecipe.setDescription("Dummy Recipe to check that everything works correctly");
        dummyRecipe.setPrepTime(0);
        dummyRecipe.setCookTime(0);
        dummyRecipe.setDifficulty(Difficulty.MODERATE);
        dummyRecipe.setDirections("Fill a rocks glass with ice. Add the vodka and coffee liqueur and give it a " +
                "quick swirl or two to mix. Top with a generous splash of coconut milk and serve. Stir together if you desire.");
        Notes dummyNotes = new Notes();
        dummyNotes.setRecipeNotes("Please, please, please don’t use cheap vodka for this. It’s the main ingredient and" +
                " you can’t make a good cocktail with bad booze. I highly recommend Tito’s Vodka as its widely available," +
                " affordable, and smooth.");
        dummyNotes.setRecipe(dummyRecipe);
        dummyRecipe.setNotes(dummyNotes);


        dummyRecipe.addIngredient(new Ingredient("vodka", BigDecimal.valueOf(2),
                ounce, dummyRecipe));
        dummyRecipe.addIngredient(new Ingredient("coffee", BigDecimal.valueOf(1),
                ounce, dummyRecipe));
        dummyRecipe.addIngredient(new Ingredient("Coconut Milk", BigDecimal.valueOf(1),
                tablespoon, dummyRecipe));

        dummyRecipe.getCategories().add(whiteRussian);
        dummyRecipe.getCategories().add(irish);
        recipeList.add(dummyRecipe);



        return recipeList;
    }
}
