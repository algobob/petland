package br.com.petland.pet;

import java.util.List;

import javax.inject.Inject;

import br.com.petland.pet.fetchPets.SearchCriteria;

public class PetService {

	@Inject
	private DataRepository<Pet> repository;
	
	public PetService(DataRepository<Pet> repository) {
		this.repository = repository;
	}

	public PetService() {
		this.repository = new PetDataRepository();
	}
	
	public Pet getPet(String id) {
		// TODO Auto-generated method stub
		return repository.getById(id);
	}

	public String addPet(Pet newPet) {
		return repository.add(newPet);
	}

	public List<Pet> fetchPets(SearchCriteria searchCriteria) {
		return repository.fetchPets(searchCriteria);
	}

}
