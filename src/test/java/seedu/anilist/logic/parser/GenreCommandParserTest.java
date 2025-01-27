package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_DELETE;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_SUPERHERO;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_DESC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_NO_SUCH_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_DESC;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.AnimeUtil.getGenreDescriptorDetails;
import static seedu.anilist.testutil.TypicalAnimes.BNHA;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.Action;
import seedu.anilist.logic.commands.GenreAddCommand;
import seedu.anilist.logic.commands.GenreCommand;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.genre.Genre;
import seedu.anilist.testutil.GenresDescriptorBuilder;

public class GenreCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenreCommand.MESSAGE_USAGE);

    private GenreCommandParser parser = new GenreCommandParser();
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ACTION_DESC_ADD, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no action specified
        assertParseFailure(parser, "1" + GENRE_DESC_SUPERHERO, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no genre specified
        assertParseFailure(parser, "1" + ACTION_DESC_ADD, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ACTION_DESC_ADD + GENRE_DESC_SUPERHERO, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ACTION_DESC_ADD + GENRE_DESC_SUPERHERO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser,
                "1 some random string" + ACTION_DESC_ADD + GENRE_DESC_SUPERHERO,
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser,
                "1 i/ string" + ACTION_DESC_ADD + GENRE_DESC_SUPERHERO,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid action
        assertParseFailure(parser,
                "1" + INVALID_ACTION_DESC + GENRE_DESC_SUPERHERO,
                String.format(Action.MESSAGE_INVALID_ACTION_FORMAT, INVALID_ACTION_NO_SUCH_ACTION));

        //invalid genre
        assertParseFailure(parser, "1" + ACTION_DESC_DELETE + INVALID_GENRE_DESC, Genre.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validAllFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ANIME;

        Anime anime = BNHA;
        GenreCommand.GenresDescriptor descriptor = new GenresDescriptorBuilder()
                .withGenre(anime.getGenres())
                .build();

        String userInputAdd = targetIndex.getOneBased() + ACTION_DESC_ADD
                + " " + getGenreDescriptorDetails(descriptor);
        GenreCommand expectedAddCommand = new GenreAddCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInputAdd, expectedAddCommand);
    }
}
