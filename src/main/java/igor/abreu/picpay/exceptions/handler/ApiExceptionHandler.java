package igor.abreu.picpay.exceptions.handler;

import igor.abreu.picpay.exceptions.PicPayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({PicPayException.class})
    public final ProblemDetail handlePicPayException(
            PicPayException ex
    ) {
        return ex.toProblemDetail();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ProblemDetail handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {

        var fieldErrors = ex.getFieldErrors()
                .stream()
                .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                .toList();

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Your request parameters didn't validate.");
        pb.setProperty("invalid-params", fieldErrors);

        return pb;
    }

    private record InvalidParam(String fieldName, String reason){}
}
