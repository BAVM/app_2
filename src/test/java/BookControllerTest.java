import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.app_2.controller.BookController;
import com.spring.app_2.entity.Book;
import com.spring.app_2.service.BookService;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    //TEST

        private MockMvc mockMvc;

        @Mock
        private BookService bookService;

        @InjectMocks
        private BookController bookController;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        }

        @Test
        void testGetAllBooks() throws Exception {
            when(bookService.getAllBook()).thenReturn(Arrays.asList(
                    new Book(1, "Libro 1", "Autor 1", "País 1"),
                    new Book(2, "Libro 2", "Autor 2", "País 2")
            ));

            mockMvc.perform(get("/api/books"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Libro 1"));
        }

        @Test
        void testGetBookById() throws Exception {
            Book book = new Book(1, "Libro Test", "Autor Test", "País Test");
            when(bookService.getBookById(1)).thenReturn(book);

            mockMvc.perform(get("/api/books/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Libro Test"));
        }

        @Test
        void testAddBook() throws Exception {
            Book book = new Book(1, "Nuevo Libro", "Nuevo Autor", "Nuevo País");

            mockMvc.perform(post("/api/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk());

            verify(bookService, times(1)).save(any(Book.class));
        }

        @Test
        void testUpdateBook() throws Exception {
            Book existingBook = new Book(1, "Viejo Libro", "Viejo Autor", "Viejo País");
            Book updatedBook = new Book(1, "Libro Actualizado", "Autor Actualizado", "País Actualizado");

            when(bookService.getBookById(1)).thenReturn(existingBook);

            mockMvc.perform(put("/api/books/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedBook)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Libro Actualizado"));
        }

    @Test
    void testDeleteBook() throws Exception {

        Book book = new Book(1, "Libro de prueba", "Autor", "País");
        when(bookService.getBookById(1)).thenReturn(book);


        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteById(1);
    }
}



