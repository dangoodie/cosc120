/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum ValentinesGifts {
    CHESS_SET, BINARY_WRIST_WATCH, BREAKING_BAD_BOX_SET, CIRCUIT_BOARD_COASTERS,
    RICK_AND_MORTY_TSHIRT, KYBER_CRYSTAL_NECKLACE, OXYTOCIN_BRACELET, TELESCOPE,
    PACMAN_SOCKS, LAB_FLASK_SHOT_GLASSES, ANATOMICAL_HEART_NECKLACE, HARRY_POTTER_QUILL;

    public String toString() {
        return switch (this) {
            case CHESS_SET -> "Chess set";
            case BINARY_WRIST_WATCH -> "Binary wrist watch";
            case BREAKING_BAD_BOX_SET -> "Breaking bad box set";
            case CIRCUIT_BOARD_COASTERS -> "Circuit board coasters";
            case RICK_AND_MORTY_TSHIRT -> "Rick and Morty t-shirt";
            case KYBER_CRYSTAL_NECKLACE -> "Kyber crystal necklace";
            case OXYTOCIN_BRACELET -> "Oxytocin bracelet";
            case TELESCOPE -> "Telescope";
            case PACMAN_SOCKS -> "Pac Man socks";
            case LAB_FLASK_SHOT_GLASSES -> "Lab flask shot glasses";
            case ANATOMICAL_HEART_NECKLACE -> "Anatomical heart necklace";
            case HARRY_POTTER_QUILL -> "Harry Potter quill";
        };
    }
}