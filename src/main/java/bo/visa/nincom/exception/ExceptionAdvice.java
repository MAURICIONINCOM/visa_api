package bo.visa.nincom.exception;

import bo.visa.nincom.shared.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<JsonResult> handleException(Exception exception) {
        //log.error(exception.getMessage(), exception);
        JsonResult result = new JsonResult(false, null, exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
