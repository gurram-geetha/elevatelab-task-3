

package com.example.bookapi;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    // Store books in memory
    private ArrayList<Book> books = new ArrayList<>();

    // Generate book IDs
    private AtomicInteger idCounter = new AtomicInteger(1);

    // 1. GET - Retrieve all books
    @GetMapping
    public ArrayList<Book> getAllBooks() {

        return books;
    }

    // 2. POST - Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        book.setId(idCounter.getAndIncrement());

        books.add(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // 3. PUT - Update book by ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable int id,
            @RequestBody Book updatedBook) {

        for (Book book : books) {

            if (book.getId() == id) {

                book.setTitle(updatedBook.getTitle());

                book.setAuthor(updatedBook.getAuthor());

                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // 4. DELETE - Remove book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {

        for (Book book : books) {

            if (book.getId() == id) {

                books.remove(book);

                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }
}
