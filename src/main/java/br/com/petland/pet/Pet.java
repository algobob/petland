package br.com.petland.pet;

import java.time.LocalDate;
import java.util.Date;

import com.google.gson.Gson;

import org.bson.types.ObjectId;

import br.com.petland.pet.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
	@Id
	private ObjectId id;
	private String name;
	private int age;
	private PetGender gender;
	private PetBreed breed;
	private PetType type;
	private LocalDate creationDate;

	public static Object fromJson(String json) {
		return new Gson().fromJson(json, Pet.class);
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}

}
