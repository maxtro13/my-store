package store.dishes.controllers.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class DishExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException ex, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage("messages.properties", new Object[0], getErrors(ex).toString(), locale));
        problemDetail.setProperty("errors",
                getErrors(ex));
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

    private List<String> getErrors(BindException bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException ex, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        return switch (ex.getStatusCode()) {
            case HttpStatus.BAD_REQUEST -> {
                problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
                problemDetail.setProperty("errors", ex.getReason());
                yield ResponseEntity.badRequest().body(problemDetail);

            }
            case HttpStatus.CONFLICT -> {
                problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
                problemDetail.setProperty("errors", ex.getReason());
                yield ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
            }
            default -> throw new IllegalStateException("Unexpected value: " + ex.getStatusCode());
        };

    }

}
