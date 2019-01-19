package br.com.petland.pet.enums;

public enum PetBreed {
    LABRADOR("LABRADOR"), VIRA_LATA("VIRA_LATA");

    private String breed;

    PetBreed(String breed){
        this.breed = breed;
    }
}