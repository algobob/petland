package br.com.petland.pet.enums;

public enum PetGender {
    FEMALE('F'), MALE('M');

    private char gender;

    PetGender(char gender){
        this.gender = gender;
    }
}