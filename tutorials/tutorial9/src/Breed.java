/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum Breed {
    JACK_RUSSELL,MOODLE,POODLE,DALMATIAN,LABRADOR,BULLDOG,PIT_BULL,ROTTWEILER,MAREMMA,WHIPPET,GERMAN_SHEPARD,CHIHUAHUA,GOLDEN_RETRIEVER;

    public String toString(){
        return switch (this){
            case MOODLE -> "Moodle";
            case POODLE -> "Poodle";
            case DALMATIAN -> "Dalmatian";
            case JACK_RUSSELL -> "Jack russell";
            case LABRADOR -> "Labrador";
            case BULLDOG -> "Bulldog";
            case ROTTWEILER -> "Rottweiler";
            case PIT_BULL -> "Pit bull";
            case MAREMMA -> "Maremma";
            case WHIPPET -> "Whippet";
            case GERMAN_SHEPARD -> "German shepard";
            case CHIHUAHUA -> "Chihuahua";
            case GOLDEN_RETRIEVER -> "Golden retriever";
        };
    }
}
