package com.jobportal.system.exceptionhandler;



import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   

    
       
        @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {


                  List details = new ArrayList<>();
          ex.getBindingResult().getAllErrors().forEach( error->details.add(error.getDefaultMessage()));
          //ErrorResponse error = new ErrorResponse("Validation Failed", details, ex.getClass().getName());

          ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,details.toString());
          return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    
  }

        @ExceptionHandler(RecordNotFoundException.class)
        public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
          List<String> details = new ArrayList<>();
          details.add(ex.getLocalizedMessage());
          ErrorResponse error = new ErrorResponse("Record Not Found", details,ex.getClass().getName());
          return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
       
  
        


  @ExceptionHandler(UserDisabledException.class)
  public final ResponseEntity<Object> handleUserDisabled(UserDisabledException ex, WebRequest request) {

    ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"user is disabled (verification of mail needed to activate) ");
    System.out.println(error);

    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
  }
        
  @ExceptionHandler(UserBadCredentialsException.class)
  public final ResponseEntity<Object> handleUserBadCredentials(UserBadCredentialsException ex, WebRequest request) {
                    
    ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid Credentials ");
    System.out.println(error);

    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
  }
  


  
  // @ExceptionHandler({ ConstraintViolationException.class })
  // public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
  //   List<String> errors = new ArrayList<String>();
  //   errors.add(ex.getCause().toString());

  //   ErrorResponse apiError = new ErrorResponse(ex.getMessage(), errors,
  //       ex.getClass().getName());
  //   return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);

    //return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  //}


  

        }
        
        
