package com.example.demo.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.domain.PaymentWithBillet;

@Service	
public class BilletService {
	
	public void fillPaymentWithBillet(PaymentWithBillet pagto, Date instant) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(instant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDueDate(cal.getTime());
	}

}
