package com.hiretrack.mapper;

import com.hiretrack.dto.BookDto;
import com.hiretrack.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book mapDtoToEntity(BookDto dto) {
        Book book=new Book();
        book.setTitle(dto.title());
        book.setSummary(dto.summary());
        return book;
    }
}
