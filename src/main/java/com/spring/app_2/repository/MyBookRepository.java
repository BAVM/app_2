package com.spring.app_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.app_2.entity.MyBookList;

@Repository
public interface MyBookRepository extends JpaRepository<MyBookList,Integer> {
}
