package br.com.petland.pet.fetchPets;

import java.time.LocalDate;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchCriteria {
	private String name;
	private String age;
	private String sex;
	private String breed;
	private LocalDate creationDate;
	
	public String toJson(){
		return new Gson().toJson(this);
	}

}
