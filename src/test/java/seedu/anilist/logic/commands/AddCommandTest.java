package seedu.anilist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.anilist.commons.core.GuiSettings;
import seedu.anilist.logic.commands.exceptions.CommandException;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.ReadOnlyAnimeList;
import seedu.anilist.model.ReadOnlyUserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.testutil.AnimeBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullAnime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_animeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAnimeAdded modelStub = new ModelStubAcceptingAnimeAdded();
        Anime validAnime = new AnimeBuilder().build();

        CommandResult commandResult = new AddCommand(validAnime).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validAnime), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAnime), modelStub.animesAdded);
    }

    @Test
    public void execute_duplicateAnime_throwsCommandException() {
        Anime validAnime = new AnimeBuilder().build();
        AddCommand addCommand = new AddCommand(validAnime);
        ModelStub modelStub = new ModelStubWithAnime(validAnime);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ANIME, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Anime naruto = new AnimeBuilder().withName("Naruto").build();
        Anime bleach = new AnimeBuilder().withName("Bleach").build();
        AddCommand addNarutoCommand = new AddCommand(naruto);
        AddCommand addBleachCommand = new AddCommand(bleach);

        // same object -> returns true
        assertTrue(addNarutoCommand.equals(addNarutoCommand));

        // same values -> returns true
        AddCommand addAotCommandCopy = new AddCommand(naruto);
        assertTrue(addNarutoCommand.equals(addAotCommandCopy));

        // different types -> returns false
        assertFalse(addNarutoCommand.equals(1));

        // null -> returns false
        assertFalse(addNarutoCommand.equals(null));

        // different anime -> returns false
        assertFalse(addNarutoCommand.equals(addBleachCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAnimeListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAnimeListFilePath(Path animeListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAnime(Anime anime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAnimeList(ReadOnlyAnimeList animeList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAnimeList getAnimeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnime(Anime anime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAnime(Anime target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAnime(Anime target, Anime editedAnime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Anime> getFilteredAnimeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAnimeList(Predicate<Anime> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single anime.
     */
    private class ModelStubWithAnime extends ModelStub {
        private final Anime anime;

        ModelStubWithAnime(Anime anime) {
            requireNonNull(anime);
            this.anime = anime;
        }

        @Override
        public boolean hasAnime(Anime anime) {
            requireNonNull(anime);
            return this.anime.isSameAnime(anime);
        }
    }

    /**
     * A Model stub that always accept the anime being added.
     */
    private class ModelStubAcceptingAnimeAdded extends ModelStub {
        final ArrayList<Anime> animesAdded = new ArrayList<>();

        @Override
        public boolean hasAnime(Anime anime) {
            requireNonNull(anime);
            return animesAdded.stream().anyMatch(anime::isSameAnime);
        }

        @Override
        public void addAnime(Anime anime) {
            requireNonNull(anime);
            animesAdded.add(anime);
        }

        @Override
        public ReadOnlyAnimeList getAnimeList() {
            return new AnimeList();
        }
    }

}
