package igor.abreu.picpay.exceptions.handler;

import igor.abreu.picpay.exceptions.PicPayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
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

}