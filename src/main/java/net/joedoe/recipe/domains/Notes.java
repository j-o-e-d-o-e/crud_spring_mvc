package net.joedoe.recipe.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipe") //avoiding circular dependencies in hashCode()
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String text;
    @OneToOne
    private Recipe recipe;

    public Notes(String text) {
        this.text = text;
    }
}