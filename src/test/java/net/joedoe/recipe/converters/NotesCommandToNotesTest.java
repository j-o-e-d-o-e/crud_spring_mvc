package net.joedoe.recipe.converters;

import net.joedoe.recipe.commands.NotesCommand;
import net.joedoe.recipe.domains.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {
    private NotesCommandToNotes converter;

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void testConvert() {
        //given
        Long ID_VALUE = 1L;
        String RECIPE_NOTES = "Notes";
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setText(RECIPE_NOTES);

        //when
        Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getText());
    }
}