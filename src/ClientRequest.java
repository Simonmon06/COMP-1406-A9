//Zeye Gu
//101036562
//Java – Generate random integers in a range. (2017-03-26). https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
public class ClientRequest {
    private String pickupLocation;
    private String dropOffLocation;

    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropOffLocation; }

    public ClientRequest (String p, String d) {
        pickupLocation = p;
        dropOffLocation = d;
    }

    public String toString() {
        return pickupLocation + " ==> " + dropOffLocation;
    }
}