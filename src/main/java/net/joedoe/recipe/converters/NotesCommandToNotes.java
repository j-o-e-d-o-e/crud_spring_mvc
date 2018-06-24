package net.joedoe.recipe.converters;

import lombok.Synchronized;
import net.joedoe.recipe.commands.NotesCommand;
import net.joedoe.recipe.domains.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }
        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setText(source.getText());
        return notes;
    }
}
