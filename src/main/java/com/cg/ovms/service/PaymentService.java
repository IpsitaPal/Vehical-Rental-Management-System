package com.cg.ovms.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.ovms.dto.Payment;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;

@Service
public interface PaymentService {

	public Payment addPayment(Payment payment) throws DuplicateRecordException;
	public Payment updatePayment(Payment payment);
	public Payment cancelPayment(int paymentId) throws RecordNotFoundException;
	public List<Payment> viewAllPayments();
	
	public Payment viewPaymentByBooking(int bookingId) throws RecordNotFoundException;
	public double calculateMonthlyRevenue(Date date1, Date date2);
	public double calculateTotalRevenue();
	public List<Payment> viewPaymentByCustomer(int customerId);
} 
