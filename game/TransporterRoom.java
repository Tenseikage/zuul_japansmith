package game;
import java.util.ArrayList;
import java.util.Random;



public class TransporterRoom extends Room {
    private ArrayList<Room> aPossibleRooms;
    private Room aChosenRoom;

    /**
     * Initialization of a transporter room
     * @param pDescription Room's description
     * @param pImage An image used to show the room
     */
    public TransporterRoom(String pDescription,final String pImage, final String pRoomName) {
        super(pDescription,pImage,pRoomName);
        this.aPossibleRooms = new ArrayList<Room>();
        this.aChosenRoom = null;
    }

    /**
     * Sets the possible destination rooms
     * @param pRooms The list of possible rooms
     */
    public void setDestinations(ArrayList<Room> pRooms) {
        this.aPossibleRooms = pRooms;
    }

    /**
     * Gives the room's exit in the given direction.
     * @return The chosen room.
     */
    public Room getChosenRoom() {
        return this.aChosenRoom;
    }


    /**
     * Sets the chosen room according to the random number
     * @param pRngNumber The random number
     */
    public void setChosenRoom(final int pRngNumber){
        this.aChosenRoom = this.aPossibleRooms.get(pRngNumber);
    }

    /**
     * Gives the room's exit in the given direction.
     * @return The chosen room.
     */
    public int getRngNumber(){
        Random vRandom = new Random();
        return vRandom.nextInt(this.aPossibleRooms.size());
    }

    /**
     * Get the index of a room in the possible destinations by its name.
     * Used by the alea command to control random behavior in tests.
     * @param pRoomName The name of the room to find
     * @return The index of the room in aPossibleRooms, or -1 if not found
     */
    public int getIndexForRoomName(final String pRoomName) {
        for (int i = 0; i < this.aPossibleRooms.size(); i++) {
            if (this.aPossibleRooms.get(i).getRoomName().equals(pRoomName)) {
                return i;
            }
        }
        return -1;
    }

    /*
     * reset the chosen room to null
     */
    public void resetChosenRoom() {
        this.aChosenRoom = null;
    }

}
