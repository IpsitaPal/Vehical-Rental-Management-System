package com.cg.ovms.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ovms.dto.Payment;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.service.PaymentService;

@RestController
@RequestMapping("/ovms")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	static Logger log = Logger.getLogger(PaymentController.class.getName());
	
	@PostMapping("/payment")
	public ResponseEntity<Payment> addPayment(@Valid @RequestBody Payment payment) { //throws DuplicateRecordException {			
		
		log.info("- addPayment - Entry ");
		payment = paymentService.addPayment(payment);
		log.info("- addPayment - Exit");
		
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@PutMapping("/payment")
	public ResponseEntity<Payment> updatePayment(@Valid @RequestBody Payment payment) throws MethodArgumentNotValidException, NoSuchMethodException, SecurityException {			
		
		if(payment.getPaymentId() == 0) {
			BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(payment, "payment");
			bindingResult.addError(new FieldError("PaymentId", "PaymentId", "PaymentId can not be null"));
			MethodParameter methodParamenter = new MethodParameter(this.getClass().getDeclaredMethod("updatePayment", Payment.class), 0);
			throw new MethodArgumentNotValidException(methodParamenter, bindingResult);
		}
		
		log.info("- updayePayment - Entry ");
		payment = paymentService.updatePayment(payment);
		log.info("- updatePayment - Exit");
		
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@DeleteMapping("/payment/{paymentId}")
	public ResponseEntity<Payment> cancelPayment(@NotEmpty @PathVariable("paymentId") Integer paymentId) throws RecordNotFoundException {
		
		log.info("- cancelPayment - Entry ");
		Payment payment = paymentService.cancelPayment(paymentId);
		log.info("- cancelPayment - Exit");
		
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@GetMapping("/payment")
	public ResponseEntity<List<Payment>> viewAllPayment() throws RecordNotFoundException{
		
		log.info("- viewAllPayment - Entry ");
		List<Payment> paymentList = paymentService.viewAllPayments();
		log.info("- viewAllPayment - Exit");
		
		return new ResponseEntity<List<Payment>>(paymentList, HttpStatus.OK);
	}
	
	@GetMapping("/payment/booking/{bookingId}")
	public ResponseEntity<Payment> viewPaymentByBooking(@PathVariable("bookingId") Integer bookingId) throws RecordNotFoundException {	
		
		log.info("- viewPaymentByBooking - Entry ");
		Payment payment = paymentService.viewPaymentByBooking(bookingId);
		log.info("- viewPaymentByBooking - Exit");
		
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}
	
	@GetMapping("/payment/{fromDate}/{tillDate}")
	public ResponseEntity<Double> calculateMonthlyRevenue(@NotEmpty @PathVariable("fromDate") String fromDate, @PathVariable("tillDate") String tillDate) throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException{
		
		log.info("- calculateMonthlyRevenue - Entry ");
		Date date1 = validateDate(fromDate);
		Date date2 = validateDate(tillDate);
		
		Double revenue = paymentService.calculateMonthlyRevenue(date1, date2);
		log.info("- calculateMonthlyRevenue - Exit");
		
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}
	
	public Date validateDate (String date) throws NoSuchMethodException, SecurityException, MethodArgumentNotValidException 
	{
		Date localDate = null;
		
		try {
			if(date.contains("."))
			{
				localDate = new SimpleDateFormat("dd.MM.yyyy").parse(date); 
			}
			else if(date.contains("-")) 
			{
				localDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			}
		}
		catch (ParseException e) {
			BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(date, "date");
			bindingResult.addError(new FieldError("Date", "Date", "Date must be valid"));
			MethodParameter methodParamenter = new MethodParameter(this.getClass().getDeclaredMethod("validateDate", String.class), 0);
			throw new MethodArgumentNotValidException(methodParamenter, bindingResult);
		}
		return localDate;
		
	}
}
