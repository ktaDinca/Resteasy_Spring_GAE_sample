package fr.latlon.rest;

import org.springframework.stereotype.Service;

@Service
public class SomeService {

	public SomeService() {
		System.out.println("fr.latlon.rest.SomeService created!");
	}
	
	public String doSth() {
		return "how ar de?";
	}
	
}
