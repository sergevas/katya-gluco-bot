package dev.sergevas.tool.katya.gluco.bot.readings.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.readings.control.ReadingsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ReadingApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ReadingsNotFoundException.class)
    public ProblemDetail handleReadingsNotFoundException(ReadingsNotFoundException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Readings Not Found");
        return problemDetail;
    }
}
