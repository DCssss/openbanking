package by.openbanking.openbankingservice.repository;

import by.openbanking.openbankingservice.models.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(
        value = "library",
        description = "the library API"
)
public interface LibraryApi {
    @ApiOperation(
            value = "This is summary",
            nickname = "getAllBooksInLibrary",
            notes = "This is a description",
            response = Book.class,
            responseContainer = "List",
            tags = {"Library"}
    )
    @ApiResponses({@ApiResponse(
            code = 200,
            message = "This means its ok",
            response = Book.class,
            responseContainer = "List"
    )})
    @RequestMapping(
            value = {"/library/books"},
            produces = {"application/json"},
            method = {RequestMethod.GET}
    )
    ResponseEntity<List<Book>> getAllBooksInLibrary();
}
