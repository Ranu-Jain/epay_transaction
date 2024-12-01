package com.epay.transaction.model;


import lombok.*;

@Data
public class ErrorDetails {
	  private Integer status;
	  private String message;


	public ErrorDetails(int i, String message) {
	}
}