package br.com.petland.pet.enums;

public enum PetGender {
    FEMALE("female"), MALE("male");

    private String gender;

    PetGender(String gender){
        this.gender = gender;
    }
}