package net.joedoe.recipe.controllers;

import net.joedoe.recipe.commands.RecipeCommand;
import net.joedoe.recipe.domains.Recipe;
import net.joedoe.recipe.services.IRecipeService;
import net.joedoe.recipe.services.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
    @Mock
    private ImageService imageService;
    @Mock
    private IRecipeService recipeService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ImageController imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void testShowUploadForm() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //then
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testSaveImageToDB() throws Exception {
        //given
        MockMultipartFile multipartFile =
                new MockMultipartFile("image-file", "testing.txt", "text/plain",
                        "Joe Doe Recipe".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        //when
        when(recipeService.findById(anyLong())).thenReturn(recipe);

        //then
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));
        verify(imageService, times(1)).saveImageToDB(eq((Recipe) recipeService.findById(anyLong())), any());
    }

    @Test
    public void testLoadImageFromDB() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        String s = "fake image text";
        byte[] bytesUnboxed = s.getBytes();
        Byte[] bytesBoxed = new Byte[s.getBytes().length];
        int i = 0;
        for (byte primByte : s.getBytes()) {
            bytesBoxed[i++] = primByte;
        }
        command.setImage(bytesBoxed);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        when(imageService.loadImageFromDB(any(RecipeCommand.class))).thenReturn(bytesUnboxed);

        //then
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipe-image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] reponseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, reponseBytes.length);
    }
}