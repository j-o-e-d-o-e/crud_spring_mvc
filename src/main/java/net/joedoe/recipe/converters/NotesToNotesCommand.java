package net.joedoe.recipe.converters;

import lombok.Synchronized;
import net.joedoe.recipe.commands.NotesCommand;
import net.joedoe.recipe.domains.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }
        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setText(source.getText());
        return notesCommand;
    }
}
