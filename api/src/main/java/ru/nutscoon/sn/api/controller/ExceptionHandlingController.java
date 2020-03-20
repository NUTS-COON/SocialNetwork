package ru.nutscoon.sn.api.controller;

import ru.nutscoon.sn.core.exception.*;
import ru.nutscoon.sn.core.model.response.BaseResponse;
import io.infinite.bobbin.Bobbin;
import io.infinite.bobbin.BobbinFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequestMapping(produces = "application/json")
@ResponseBody
public class ExceptionHandlingController {

    private final Bobbin logger = (Bobbin) new BobbinFactory().getLogger(ExceptionHandlingController.class.getCanonicalName());

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({InvalidOperationException.class})
    public BaseResponse conflict(Exception e) {
        return getErrorResponse(e);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({NotAuthenticatedException.class, WrongPersonTokenException.class})
    public BaseResponse notAuthorized(Exception e) {
        return getErrorResponse(e);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public BaseResponse notFound(Exception e) {
        return getErrorResponse(e);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public BaseResponse accessDenied(Exception e) {
        return getErrorResponse(e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadCredentialsException.class})
    public BaseResponse badRequest(Exception e) {
        return getErrorResponse(e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResponse validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Collection<String> errors = fieldErrors
                .stream()
                .map(fieldError -> "Filed: " + fieldError.getField() + ". Error: " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return BaseResponse.createError(BaseResponse.class, String.join("; ", errors));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public BaseResponse internalServerError(Exception e) {
        logger.error("Unhandled error", e);
        return getErrorResponse(e);
    }

    private BaseResponse getErrorResponse(Exception e) {
        return BaseResponse.createError(BaseResponse.class, e.getMessage());
    }
}
