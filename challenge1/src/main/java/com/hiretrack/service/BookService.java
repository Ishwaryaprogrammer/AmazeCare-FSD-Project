package com.hiretrack.service;

import com.hiretrack.dto.BookDto;
import com.hiretrack.mapper.BookMapper;
import com.hiretrack.model.Book;
import com.hiretrack.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private AuthorService authorService;

    public void add(int authorId, BookDto dto) {
        Book book=bookMapper.mapDtoToEntity(dto);
        book.setAuthor(authorService.getById(authorId));
        bookRepository.save(book);
    }
}
