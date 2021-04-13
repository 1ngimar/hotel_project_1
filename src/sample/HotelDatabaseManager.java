package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class HotelDatabaseManager {
    private ArrayList<Hotel> allHotels;
    private User dummyUser;
    private ArrayList<HotelBooking> allBookings;
    private ArrayList<Room> allRoomsForHotel;
    Connection conn = new DBFactory().connect();

    public HotelDatabaseManager() {

    }

    public ArrayList<Hotel> getAllHotels() {
        allHotels.clear();
        String sqlAllHotels = "SELECT * FROM HOTEL";

        try {
            Statement stmtHotels = conn.createStatement();
            ResultSet rsHotels = stmtHotels.executeQuery(sqlAllHotels);

            while (rsHotels.next()) {
                allRoomsForHotel.clear();
                Hotel h = new Hotel();
                // Set hotel id into the hotel object
                int hotelID = rsHotels.getInt("hotelID");
                h.setHotel_id(hotelID);

                // Set HotelName into the hotel object
                String hotelName = rsHotels.getString("HotelName");
                h.setHotel_name(hotelName);

                // Set hotel Location into the hotel object
                String hotelLocation = rsHotels.getString("HotelLocation");
                h.setHotel_location(hotelLocation);

                // Set hotel address into the hotel object
                String hotelAddress = rsHotels.getString("HotelAddress");
                h.setHotel_address(hotelAddress);

                // Set hotel postal code into the hotel object
                int hotelPostCode = rsHotels.getInt("HotelPostCode");
                h.setHotel_postal_code(hotelPostCode);

                // Set hotel phone number into the hotel object
                int hotelPhone = rsHotels.getInt("HotelPhoneNumber");
                h.setHotel_phone_number(hotelPhone);

                // Set hotel address into the hotel object
                int hotelStarRating = rsHotels.getInt("HotelStarRating");
                Hotel.StarRating resultSR;
                switch (hotelStarRating) {
                    case 3:
                        resultSR = Hotel.StarRating.THREE;
                        break;
                    case 4:
                        resultSR = Hotel.StarRating.FOUR;
                        break;
                    case 5:
                        resultSR = Hotel.StarRating.FIVE;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + hotelStarRating);
                }
                h.setHotel_star_rating(resultSR);

                // Set hotel amenities into the hotel object
                String hotelAmenityString = rsHotels.getString("HotelAmenities");
                String[] hotelAmenityStringSplitted = hotelAmenityString.split(", ", 0);
                Hotel.HotelAmenities[] resultHA = new Hotel.HotelAmenities[hotelAmenityStringSplitted.length];
                String singleHotelAmenity;
                for (int i = 0; i < hotelAmenityStringSplitted.length; i++) {
                    singleHotelAmenity = hotelAmenityStringSplitted[i];
                    resultHA[i] = Hotel.HotelAmenities.valueOf(singleHotelAmenity);
                }
                h.setHotel_amenities(resultHA);

                // Set hotel type into the hotel object (1 = economy, 2 = comfort)
                int hotelType = rsHotels.getInt("HotelType");
                h.setHotel_type(hotelType);

                // Set hotel base price into the hotel object
                int hotelBasePrice = rsHotels.getInt("HotelBasePrice");
                h.setHotel_base_price(hotelBasePrice);


                // Now, for each hotel we will assign all the rooms for that hotel
                String sqlRoomsForHotel = "SELECT * FROM ROOM WHERE RoomHotelID = " + hotelID;
                Statement stmtRooms = conn.createStatement();
                ResultSet rsRooms = stmtRooms.executeQuery(sqlRoomsForHotel);
                while (rsRooms.next()) {
                    Room r = new Room();

                    // Set room ID into the room object
                    int roomID = rsRooms.getInt("RoomID");
                    r.setRoom_id(roomID);

                    // Set hotel ID into the room object
                    int roomHotelID = rsRooms.getInt("RoomHotelID");
                    r.setHotel_id(roomHotelID);

                    // Set room category AND room capacity into the room object
                    int roomCapacity = rsRooms.getInt("RoomCapacity");
                    Room.RoomCategory resultRC;
                    switch (roomCapacity) {
                        case 1:
                            resultRC = Room.RoomCategory.SINGLE;
                            break;
                        case 2:
                            resultRC = Room.RoomCategory.DOUBLE;
                            break;
                        case 3:
                        case 4:
                            resultRC = Room.RoomCategory.FAMILY;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + roomCapacity);
                    }
                    r.setRoom_category(resultRC);
                    r.setRoom_capacity(resultRC);

                    // Set room price multiplier into the room object
                    double roomPriceMultiplier = rsRooms.getDouble("RoomPriceMultiplier");
                    r.setRoom_price_multiplier(roomPriceMultiplier);

                    // Set room amenities into the room object
                    String roomAmenityString = rsRooms.getString("RoomAmenities");
                    String[] roomAmenityStringSplitted = roomAmenityString.split(", ", 0);
                    Room.RoomAmenities[] resultRA = new Room.RoomAmenities[roomAmenityStringSplitted.length];
                    String singleRoomAmenity;
                    for (int i = 0; i < roomAmenityStringSplitted.length; i++) {
                        singleRoomAmenity = roomAmenityStringSplitted[i];
                        resultRA[i] = Room.RoomAmenities.valueOf(singleRoomAmenity);
                    }
                    r.setRoom_amenities(resultRA);
                    //Þetta að neðan var von mín um að þetta gæti virkað eins og inni í DataFactory en þetta virkar ekki
                    //Room.RoomAmenities resultRA = new Room.RoomAmenities[]{roomAmenityString};


                    // Set room occupancy into the room object
                    String sqlOccupancyForRoom = "SELECT * FROM BOOKING_ROOM WHERE BookingRoomID = " + roomID;
                    Statement stmtOccupancyRoom = conn.createStatement();
                    ResultSet rsBookingRoom = stmtOccupancyRoom.executeQuery(sqlOccupancyForRoom);
                    int i = 0;
                    String arrDateString, depDateString;
                    LocalDate arrDate, depDate;
                    ArrayList<ArrayList<LocalDate>> roomOccupancy = new ArrayList<>();
                    while (rsBookingRoom.next()) {
                        arrDateString = rsBookingRoom.getString("BookingArrDate");
                        depDateString = rsBookingRoom.getString("BookingDepDate");
                        arrDate = LocalDate.parse(arrDateString);
                        depDate = LocalDate.parse(depDateString);
                        roomOccupancy.set(i, new ArrayList<LocalDate>());
                        roomOccupancy.get(i).add(arrDate);
                        roomOccupancy.get(i).add(depDate);
                        i++;
                    }
                    r.setRoom_occupancy(roomOccupancy);

                    allRoomsForHotel.add(r);
                }
                h.setHotel_room_list(allRoomsForHotel);

                allHotels.add(h);
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL Selectall()");
            System.out.println(e.getMessage());
        }
        return allHotels;
    }

    //TODO klára að búa þetta fall til
    //public void addNewBooking(HotelBooking hb){

    //}
}
