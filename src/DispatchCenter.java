//Zeye Gu
//101036562
//Java – Generate random integers in a range. (2017-03-26). https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
import java.util.*;

public class DispatchCenter {
    public static String[] AREA_NAMES = {"Downtown", "Airport", "North", "South", "East", "West"};

    private int[][]  stats; // You'll need this for the last part of the assignment
    private HashMap<Integer,Taxi> taxis;
    private HashMap<String,ArrayList<Taxi>> areas;


    // Constructor
    public DispatchCenter() {
        // You'll need this for the last part of the assignment
        stats = new int[AREA_NAMES.length][AREA_NAMES.length];
        taxis = new HashMap<Integer,Taxi>();
        areas = new  HashMap<String,ArrayList<Taxi>>();
        for(int i=0; i<50;i++){
            addTaxi(new Taxi(getaRandomNumberInRange(100,999)),AREA_NAMES[getaRandomNumberInRange(0,5)]);        }
    }

    public  HashMap<Integer,Taxi> getTaxis (){return taxis;}
    public HashMap<String,ArrayList<Taxi>> getAreas(){return areas;}

    // You'll need this for the last part of the assignment
    public int[][]   getStats() { return stats; }


    // Update the statistics for a taxi going from the pickup location to the dropoff location
    public void updateStats(String pickup, String dropOff) {
        int pickupindex =0;
        int dropoffindex =0;
        for(int i =0;i<AREA_NAMES.length;i++){
            if(AREA_NAMES[i]==pickup){
                pickupindex= i;
            }
            if(AREA_NAMES[i]==dropOff){
                dropoffindex= i;
            }
        }
        stats[dropoffindex][pickupindex] +=1;

    }

    // Determine the travel times from one area to another
    public static int computeTravelTimeFrom(String pickup, String dropOff) {
        int[][] traveltimelist={
                                {10,40,20,20,20,20},
                                {40,10,40,40,20,60},
                                {20,40,10,40,20,20},
                                {20,40,40,10,20,20},
                                {20,20,20,20,10,40},
                                {20,60,20,20,40,10}
        };
        int pickupindex =0;
        int dropoffindex =0;
        for(int i =0;i<AREA_NAMES.length;i++){
            if(AREA_NAMES[i]==pickup){
                pickupindex= i;
            }
            if(AREA_NAMES[i]==dropOff){
               dropoffindex= i;
            }
        }
        int requiredtime = traveltimelist[dropoffindex][pickupindex];


        return 0+requiredtime;
    }

    // Add a taxi to the hashmaps
    public void addTaxi(Taxi aTaxi, String area) {
        taxis.put(aTaxi.getPlateNumber(),aTaxi);
        //if there is no area matching this taxi's area, make a new area
        if(!areas.containsKey(area))
            areas.put(area,new ArrayList<Taxi>());
        //add
        areas.get(area).add(aTaxi);
    }

    // Return a list of all available taxis within a certain area
    private ArrayList<Taxi> availableTaxisInArea(String s) {
        ArrayList<Taxi> result = new ArrayList<Taxi>();
        for(Taxi t : areas.get(s)) {
            if (t.getAvailable()){
                result.add(t);
            }
        }

        return result;
    }

    // Return a list of all busy taxis
    public ArrayList<Taxi> getBusyTaxis() {
        ArrayList<Taxi> result = new ArrayList<Taxi>();

        for (Taxi t:taxis.values()){
            if (!t.getAvailable()){
                result.add(t);
            }
    }
        return result;
    }

    // Find a taxi to satisfy the given request
    public Taxi sendTaxiForRequest(ClientRequest request) {
        String requestArea = request.getPickupLocation();
        if (availableTaxisInArea(requestArea).size()!=0) {
            Taxi sentedTaxi = availableTaxisInArea(requestArea).get(0);
            areas.get(requestArea).remove(sentedTaxi);
            areas.get(requestArea).add(sentedTaxi);
            sentedTaxi.setAvailable(false);
            int time =computeTravelTimeFrom(requestArea,request.getDropoffLocation());
            sentedTaxi.setEstimatedTimeToDest(time);
            updateStats(requestArea,request.getDropoffLocation());
            return sentedTaxi;
        }
        else if (availableTaxisInArea(requestArea).size()==0){
            int randomNum = getaRandomNumberInRange(0,5);
            while (availableTaxisInArea(AREA_NAMES[randomNum]).size()==0){
                int newrandomnum = getaRandomNumberInRange(0,5);
                randomNum = newrandomnum;
            }
            Taxi sentedTaxi = availableTaxisInArea(AREA_NAMES[randomNum]).get(0);
            areas.get(AREA_NAMES[randomNum]).remove(sentedTaxi);
            areas.get(AREA_NAMES[randomNum]).add(sentedTaxi);
            sentedTaxi.setAvailable(false);
            int time =computeTravelTimeFrom(requestArea,request.getDropoffLocation());
            int additionaltime = 0;
            additionaltime= computeTravelTimeFrom(AREA_NAMES[randomNum],requestArea);
            int combinedtime =0;
            combinedtime =time+additionaltime;
            sentedTaxi.setEstimatedTimeToDest(combinedtime);
            updateStats(requestArea,request.getDropoffLocation());

            return sentedTaxi;
        }
        else{
            return null;
    }}

    //Java – Generate random integers in a range. (2017-03-26). https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
    private static int getaRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

    }
}