package com.wpy.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaseApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(BaseApplication.class, args);
//	}
		public static void main(String[] args) {
			
			int days = 27;
			double billAmount = 29755.20;
			double interestRate = 0.06;
//			double billRate = Arith.div(Arith.mul(Arith.mul(billamount, days), interest),
//					360.00, 2);
//			double amount = Arith.sub(billamount, billRate);
			double perDay = 360;
			double interest = Arith.div(Arith.mul(Arith.mul(billAmount, days), interestRate),perDay, 2);
			double amount = Arith.sub(billAmount, interest);
			System.out.println(billAmount-amount);
		}
}
