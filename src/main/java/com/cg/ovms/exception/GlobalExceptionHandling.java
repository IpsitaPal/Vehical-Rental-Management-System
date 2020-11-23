package com.cg.ovms.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

	static Logger log = Logger.getLogger(GlobalExceptionHandling.class.getName());
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		log.error("Input Not Valid");
		
		System.out.println(ex.getBindingResult().getFieldErrorCount());
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", new Date());
		body.put("Status", status.value());
		
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(x -> x.getDefaultMessage()).collect(Collectors.toList());
		
		body.put("Errors", errors);
		
		return new ResponseEntity<> (body, headers, status);
	}

	@ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException exception, WebRequest webRequest) {
		
		log.error("No Record Found");
		
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(404);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());
  
        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.NOT_FOUND);
        
    }

	@ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Object> handleDuplicateRecordException(DuplicateRecordException exception, WebRequest webRequest) {
		
		log.error("Duplicate Record Found");
		
		System.out.println(webRequest.toString());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(302);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());
  
        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.FOUND);
        
    }

	@ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleDatabaseException(DatabaseException exception, WebRequest webRequest) {
		
		log.error("Database Exception Occured");
		
		System.out.println(webRequest.toString());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(417);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());
  
        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.EXPECTATION_FAILED);
        
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest) {

        System.out.println(webRequest.toString());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(422);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());

        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.NOT_FOUND);

    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefaultException(Exception exception, WebRequest webRequest) {
		
		log.error("Internal Server Error Occured");
		
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(500);
        exceptionResponse.setTime(new Date());
        exceptionResponse.setMessage(exception.getMessage());
  
        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }

}
