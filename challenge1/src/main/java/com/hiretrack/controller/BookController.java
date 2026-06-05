package com.hiretrack.controller;

import com.hiretrack.dto.BookDto;
import com.hiretrack.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {


    private BookService bookService;
    @PostMapping("/{authorId}")
    public void add(
            @PathVariable int authorId,
            @Valid @RequestBody BookDto dto
    ){
        bookService.add(authorId,dto);
    }

//
//Create a POST API to add Book record in the DB.
//Read authorId as path variable.
//Create a DTO to read title and summary from the API caller.
//validate it using validation annotations.
//Handle the Exception
}
