package seedu.anilist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_GENRE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.model.genre.Genre;

/**
 * Represents a command that affects the genres of an Anime
 */
public abstract class GenreCommand extends Command {

    public static final String COMMAND_WORD = "genre";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/Deletes a genre from an Anime. "
            + "Parameters:\n"
            + "INDEX (must be a positive integer) "
            + PREFIX_ACTION + "ACTION "
            + PREFIX_GENRE + "GENRE "
            + "[" + PREFIX_GENRE + "GENRE]\n"
            + "Example of adding Genres: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_ACTION + "add "
            + PREFIX_GENRE + "Fantasy "
            + PREFIX_GENRE + "Action\n"
            + "Example of deleting Genres: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_ACTION + "delete "
            + PREFIX_GENRE + "Fantasy "
            + PREFIX_GENRE + "Action";


    private final GenreCommand.GenresDescriptor genresDescriptor;
    private final Index index;

    /**
     * @param index of the anime in the filtered anime list to edit
     * @param genresDescriptor details to update the anime's episode with
     */
    public GenreCommand(Index index, GenreCommand.GenresDescriptor genresDescriptor) {
        requireNonNull(index);
        requireNonNull(genresDescriptor);

        this.index = index;
        this.genresDescriptor = genresDescriptor;
    }

    public Index getIndex() {
        return index;
    }

    public GenresDescriptor getGenresDescriptor() {
        return genresDescriptor;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenreCommand // instanceof handles nulls
                && index.equals(((GenreCommand) other).index)
                && genresDescriptor.equals(((GenreCommand) other).genresDescriptor));
    }

    /**
     * Stores the set of genres to be modified.
     */
    public static class GenresDescriptor {
        private Set<Genre> genres;

        public GenresDescriptor() {}

        /**
         * Copy constructor.
         */
        public GenresDescriptor(GenreCommand.GenresDescriptor toCopy) {
            setGenres(toCopy.genres);
        }

        /**
         * Returns true if at least one genre is changed.
         */
        public boolean areGenresUpdated() {
            return genres != null;
        }

        /**
         * Sets {@code genres} to this object's {@code genres}.
         * A defensive copy of {@code genres} is used internally.
         */
        public void setGenres(Set<Genre> genres) {
            this.genres = (genres != null) ? new HashSet<>(genres) : null;
        }

        /**
         * Returns an unmodifiable genre set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code genres} is null.
         */
        public Optional<Set<Genre>> getGenres() {
            return (genres != null) ? Optional.of(Collections.unmodifiableSet(genres)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof GenreCommand.GenresDescriptor)) {
                return false;
            }

            // state check
            GenreCommand.GenresDescriptor e = (GenreCommand.GenresDescriptor) other;

            return getGenres().equals(e.getGenres());
        }

        @Override
        public String toString() {
            String changedGenres = Arrays.toString(genres.toArray());
            return changedGenres.substring(1, changedGenres.length() - 1);
        }
    }
}
