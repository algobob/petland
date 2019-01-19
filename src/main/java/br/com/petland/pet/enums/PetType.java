package br.com.petland.pet.enums;

public enum PetType {
    DOG("DOG"), CAT("CAT");

    private String type;

    PetType(String type){
        this.type = type;
    }
}