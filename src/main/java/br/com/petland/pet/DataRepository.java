package br.com.petland.pet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.google.inject.ImplementedBy;

import br.com.petland.pet.fetchPets.SearchCriteria;

// @ImplementedBy(PetDataRepository.class)
public interface DataRepository<T> {

	public T getById(String id);

	public String add(T data);

	public List<Pet> fetchPets(SearchCriteria searchCriteria);

}
