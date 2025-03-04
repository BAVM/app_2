import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.app_2.controller.MyBookListController;
import com.spring.app_2.entity.MyBookList;
import com.spring.app_2.service.MyBookListService;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MyBookListControllerTest {

    // TEST


        private MockMvc mockMvc;

        @Mock
        private MyBookListService myBookListService;

        @InjectMocks
        private MyBookListController myBookListController;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(myBookListController).build();
        }

        @Test
        void testGetAllMyBooks() throws Exception {
            when(myBookListService.getAllMyBooks()).thenReturn(Arrays.asList(
                    new MyBookList(1, "Libro 1", "Autor 1", "País 1"),
                    new MyBookList(2, "Libro 2", "Autor 2", "País 2")
            ));

            mockMvc.perform(get("/api/mybooks"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Libro 1"));
        }

        @Test
        void testAddToMyBooks() throws Exception {
            MyBookList book = new MyBookList(1, "Libro Favorito", "Autor Favorito", "País Favorito");

            mockMvc.perform(post("/api/mybooks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk());

            verify(myBookListService, times(1)).saveMyBooks(any(MyBookList.class));
        }

        @Test
        void testDeleteMyBook() throws Exception {
            mockMvc.perform(delete("/api/mybooks/1"))
                    .andExpect(status().isNoContent());

            verify(myBookListService, times(1)).deleteById(1);
        }
}


