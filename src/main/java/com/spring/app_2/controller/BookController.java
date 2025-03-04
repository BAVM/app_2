package com.spring.app_2.controller;

import com.spring.app_2.entity.MyBookList;
import com.spring.app_2.entity.Book;
import com.spring.app_2.service.BookService;
import com.spring.app_2.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;


//@Controller
@RestController
@RequestMapping("/api/books")
public class BookController {

    // DESHABILITAR PARA FLUX

    /*@Autowired
    private BookService service;

    @Autowired
    private MyBookListService myBookService;


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = service.getAllBook();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        service.save(book);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Optional<Book> book = Optional.ofNullable(service.getBookById(id));
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        Book book = service.getBookById(id);
        if (book != null) {
            book.setName(updatedBook.getName());
            book.setAuthor(updatedBook.getAuthor());
            book.setCountry(updatedBook.getCountry());
            service.save(book);
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        Book book = service.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mylist/{id}")
    public ResponseEntity<MyBookList> addToMyList(@PathVariable int id) {
        Book book = service.getBookById(id);
        if (book != null) {
            MyBookList myBook = new MyBookList(book.getId(), book.getName(), book.getAuthor(), book.getCountry());
            myBookService.saveMyBooks(myBook);
            return ResponseEntity.ok(myBook);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updatePartialBook(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Book book = service.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    book.setName((String) value);
                    break;
                case "author":
                    book.setAuthor((String) value);
                    break;
                case "country":
                    book.setCountry((String) value);
                    break;
            }
        });

        service.save(book);
        return ResponseEntity.ok(book);
    }*/



    // FLUX
    @Autowired
    private BookService service;

    @Autowired
    private MyBookListService myBookService;

    @GetMapping
    public Flux<Book> getAllBooks() {
        return Flux.fromIterable(service.getAllBook());
    }

    @PostMapping
    public Mono<ResponseEntity<Book>> addBook(@RequestBody Book book) {
        service.save(book);
        return Mono.just(ResponseEntity.ok(book));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Book>> getBookById(@PathVariable int id) {
        return Mono.fromCallable(() -> service.getBookById(id)) // Llamada segura a Optional<Book>
                .flatMap(optionalBook -> optionalBook
                        .map(book -> Mono.just(ResponseEntity.ok(book))) // Si el libro existe
                        .orElseGet(() -> Mono.just(ResponseEntity.notFound().build()))); // Si el libro no existe
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Book>> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        return Mono.justOrEmpty(service.getBookById(id))
                .flatMap(book -> {
                    book.setName(updatedBook.getName());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCountry(updatedBook.getCountry());
                    service.save(book);
                    return Mono.just(ResponseEntity.ok(book));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable int id) {
        return Mono.fromRunnable(() -> service.deleteById(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping("/mylist/{id}")
    public Mono<ResponseEntity<MyBookList>> addToMyList(@PathVariable int id) {
        return Mono.justOrEmpty(service.getBookById(id))
                .flatMap(book -> {
                    MyBookList myBook = new MyBookList(book.getId(), book.getName(), book.getAuthor(), book.getCountry());
                    myBookService.saveMyBooks(myBook);
                    return Mono.just(ResponseEntity.ok(myBook));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
