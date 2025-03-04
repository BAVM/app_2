package com.spring.app_2.controller;

import com.spring.app_2.entity.MyBookList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.spring.app_2.service.MyBookListService;

import java.util.List;
import java.util.Optional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



//@Controller
@RestController
@RequestMapping("/api/mybooks")
public class MyBookListController {

    // DESHABILITAR PARA FLUX

    /*@Autowired
    private MyBookListService service;

    @GetMapping
    public ResponseEntity<List<MyBookList>> getAllMyBooks() {
        List<MyBookList> books = service.getAllMyBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<MyBookList> addToMyBooks(@RequestBody MyBookList book) {
        service.saveMyBooks(book);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyBookList> getMyBookById(@PathVariable int id) {
        Optional<MyBookList> book = service.getAllMyBooks().stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyBook(@PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }*/


    //FLUX
    @Autowired
    private MyBookListService service;

    @GetMapping
    public Flux<MyBookList> getAllMyBooks() {
        return Flux.fromIterable(service.getAllMyBooks());
    }

    @PostMapping
    public Mono<ResponseEntity<MyBookList>> addToMyBooks(@RequestBody MyBookList book) {
        service.saveMyBooks(book);
        return Mono.just(ResponseEntity.ok(book));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MyBookList>> getMyBookById(@PathVariable int id) {
        return Flux.fromIterable(service.getAllMyBooks())
                .filter(b -> b.getId() == id)
                .next()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMyBook(@PathVariable int id) {
        service.deleteById(id);
        return Mono.just(ResponseEntity.noContent().build());
    }



}
