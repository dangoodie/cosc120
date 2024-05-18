/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum RomanticActivities {
    SELECT,BOARD_GAMES,COMPUTER_OR_VIDEO_GAMES,COOK_AT_HOME,BOOK_CLUB,DRIVE_IN_MOVIE,
    BUILD_LEGO,VISIT_SPACE_STATION,PLAY_TRIVIA,READ_COMICS,CARD_GAME, NERF_WAR,
    POKEMON_GO,LASER_TAG,BINGE_TV_SHOWS,STAR_GAZING,SCAVENGER_HUNT,COSC120;

    /**
     * @return a prettified description of the range of geeky romantic activities
     */
    public String toString(){
        return switch (this) {
            case SELECT -> "Select preferred romantic activity";
            case BOARD_GAMES -> "Play board games";
            case COMPUTER_OR_VIDEO_GAMES -> "Play computer or video games together";
            case COOK_AT_HOME -> "Cook at home together";
            case BOOK_CLUB -> "Join/participate in a book club";
            case DRIVE_IN_MOVIE -> "Watch a drive-in movie.";
            case BUILD_LEGO -> "Build lego";
            case VISIT_SPACE_STATION -> "Visit a space station";
            case PLAY_TRIVIA -> "Play trivia";
            case READ_COMICS -> "Read comics";
            case CARD_GAME -> "Play card games";
            case NERF_WAR -> "Nerf war";
            case POKEMON_GO -> "Pokemon GO";
            case LASER_TAG -> "Laser tag";
            case BINGE_TV_SHOWS -> "Binge on favourite tv shows";
            case STAR_GAZING -> "Gaze at the stars";
            case SCAVENGER_HUNT -> "Scavenger hunt";
            case COSC120 -> "Completing COSC120 tutorial questions";
        };
    }
}