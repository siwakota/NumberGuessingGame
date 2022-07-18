package ca.sheridancollege.siwakota.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class NumberGuessingGame extends Counter implements Serializable {

	private int answer = (int) (Math.random()*(10-1)) + 1;	// Generate random number in between 1 and 10.
	private String userInput;
	private  String compare;

	public int counter()
	{
		setCounter(getCounter()+1);
		return getCounter();
	}

}


