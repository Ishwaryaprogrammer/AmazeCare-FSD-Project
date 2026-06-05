package com.hiretrack.service;

import com.hiretrack.exception.ResourceNotFoundException;
import com.hiretrack.model.Author;
import com.hiretrack.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository authorRepository;

    public Author getById(int authorId) {

        return authorRepository.findById(authorId).orElseThrow(
                ()-> new ResourceNotFoundException("Author Id is not found")
        );
    }
}
