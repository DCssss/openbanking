package by.openbanking.openbankingservice.controller;

import by.openbanking.openbankingservice.repository.LibraryApi;
import by.openbanking.openbankingservice.models.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/hello")
@Api(value = "hello", description = "Sample hello world application")
public class TestController {



    @ApiOperation(value = "Just to test the sample test api of My App Service")
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    // @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        return "Hello to check Swagger UI";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/test1", method = GET)
    @ApiOperation(value = "My App Service get test1 API", position = 1)
    public String test1() {
        System.out.println("Testing");
        return "Tanuj";
    }

    @RestController
    public static class LibraryController implements LibraryApi {
        @Override
        public ResponseEntity<List<Book>> getAllBooksInLibrary() {
            List<Book> books = new ArrayList<>();
            Book book = new Book();
            book.setName("Harry P");
            book.setBookAuthor("JK");

            Book book3 = new Book();
            book3.setBookAuthor("Gorky");
            book3.setName("Na dne");

            Book book2 = new Book();
            book2.setName("Tik Tok");
            book2.setBookAuthor("Pidoras");

            books.add(book);
            books.add(book2);

            books.add(book3);

            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }

   }
