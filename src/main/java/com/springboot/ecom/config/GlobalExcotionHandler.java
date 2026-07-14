package com.springboot.ecom.config;

import com.springboot.ecom.dto.response.ErrorMessageDto;
import com.springboot.ecom.exception.ResourseNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExcotionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult result=e.getBindingResult();
        List<FieldError> fieldErrorList=result.getFieldErrors();
        Map<String,String> map=new HashMap<>();
        fieldErrorList.forEach(err->map.put(err.getField(),err.getDefaultMessage()));
        return ResponseEntity
                .badRequest()
                .body(map);

    }

@ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleResourseNotFoundException(ResourseNotFoundException e){
        return ResponseEntity
                .badRequest()
                .body( new ErrorMessageDto(e.getMessage()));

}
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ErrorMessageDto>handleIllegalArgumentException(IllegalArgumentException e){
        return  ResponseEntity
                .badRequest()
                .body(new ErrorMessageDto(e.getMessage()));
}
}
