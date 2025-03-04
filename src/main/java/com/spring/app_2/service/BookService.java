package com.spring.app_2.service;

import com.spring.app_2.repository.BookRepository;
import com.spring.app_2.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    @Autowired
    private BookRepository bRepo;

    public void save(Book b) {
        bRepo.save(b);
    }

    public List<Book> getAllBook(){
        return bRepo.findAll();
    }

    //habilitar para flux

    public Optional<Book> getBookById(int id) {
        return bRepo.findById(id);
    }

    public void deleteById(int id) {
        bRepo.deleteById(id);
    }


    /// Pruebas postman y test //Deshabilitar para FLUX

    /*public Book getBookById(int id) {
        return bRepo.findById(id).get();
    }
    public void deleteById(int id) {
        bRepo.deleteById(id);
    }*/

}
