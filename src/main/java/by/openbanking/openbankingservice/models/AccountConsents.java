package by.openbanking.openbankingservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@ApiModel(
        description = "This is the book model"
)
@Validated
public class AccountConsents {
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("bookAuthor")
    private String bookAuthor = null;

    public AccountConsents() {
    }

    public AccountConsents name(String name) {
        this.name = name;
        return this;
    }

    @ApiModelProperty("The name of book")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountConsents bookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    @ApiModelProperty("name of author")
    public String getBookAuthor() {
        return this.bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AccountConsents book = (AccountConsents)o;
            return Objects.equals(this.name, book.name) && Objects.equals(this.bookAuthor, book.bookAuthor);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.bookAuthor});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Book {\n");
        sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
        sb.append("    bookAuthor: ").append(this.toIndentedString(this.bookAuthor)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
