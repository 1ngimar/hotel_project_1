package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class HotelDatabaseManager {
    private ArrayList<Hotel> allHotels = new ArrayList<>();
    private User dummyUser;
    private ArrayList<HotelBooking> allBookings;
    //private ArrayList<Room> allRoomsForHotel = new ArrayList<>();
    private HotelBooking booking;
    private Hotel hotel;
    //Connection conn = new DBFactory().connect(); Færði þetta inn í try inni í getAllHotels()


    public HotelDatabaseManager() {

    }

    public ArrayList<Hotel> getAllHotels() {
        allHotels.clear();
        String sqlAllHotels = "SELECT * FROM HOTEL";

        try {
            Connection conn = new DBFactory().connect();
            Statement stmtHotels = conn.createStatement();
            ResultSet rsHotels = stmtHotels.executeQuery(sqlAllHotels);

            while (rsHotels.next()) {
                ArrayList<Room> allRoomsForHotel = new ArrayList<>();
                //allRoomsForHotel.clear();
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
                    ArrayList<LocalDate> temp = new ArrayList<>();
                    while (rsBookingRoom.next()) {
                        temp.clear();
                        arrDateString = rsBookingRoom.getString("BookingArrDate");
                        depDateString = rsBookingRoom.getString("BookingDepDate");
                        arrDate = LocalDate.parse(arrDateString);
                        depDate = LocalDate.parse(depDateString);
                        //roomOccupancy.set(i, new ArrayList<>()); // þetta gefur villu
                        temp.add(arrDate);
                        temp.add(depDate);
                        roomOccupancy.add(temp);
                        //roomOccupancy.get(i).add(arrDate);
                        //roomOccupancy.get(i).add(depDate);
                        i++;
                    }
                    r.setRoom_occupancy(roomOccupancy);

                    allRoomsForHotel.add(r);
                }
                h.setHotel_room_list(allRoomsForHotel);

                allHotels.add(h);
            }
            conn.close(); // Held að þetta vanti hér (ILG)
        } catch (SQLException e) {
            System.out.println("Error in SQL Selectall()");
            System.out.println(e.getMessage());
        }
        return allHotels;
    }

    public void addNewBooking(HotelBooking hb) {
        this.booking = hb;
        this.hotel = hb.getBooking_hotel();
        int hotelID = hotel.getHotel_id();
        User user = booking.getBooking_user();
        int userID = user.getUser_id();
        int numOfGuests = booking.getBooking_num_of_guests();
        //boolean paymentFinalized = booking.isBooking_payment_finalized();
        boolean paymentFinalized = false;
        int bookingID = 0;
        ArrayList<Room> selectedRooms = new ArrayList<>();

        String bookingInsertString = ("INSERT INTO BOOKING " +
                "(BookingHotelID, BookingUserID, BookingNumOfGuests, BookingPaymentFinalized) VALUES (" +
                hotelID + ", " + userID + ", " + numOfGuests + ", " + paymentFinalized + ")");

        try { // Add the booking into the BOOKING column in the hotelDataBase.db file
            Connection conn = new DBFactory().connect();
            Statement stmtBooking = conn.createStatement();
            stmtBooking.executeUpdate(bookingInsertString);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL addNewBooking() in BOOKING");
            System.out.println(e.getMessage());
        }

        try { // Get the auto-generated bookingID from the hotelDataBase.db file
            String sqlThisBookingID = ("SELECT MAX(BookingID) FROM BOOKING");
            Connection conn = new DBFactory().connect();
            Statement stmtThisBookingID = conn.createStatement();
            ResultSet rsBookingID = stmtThisBookingID.executeQuery(sqlThisBookingID);
            bookingID = rsBookingID.getInt("MAX(BookingID)");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL addNewBooking() in getting bookingID");
            System.out.println(e.getMessage());
        }

        String bookingArrDate = booking.getBooking_arr_date().toString(); // (yyyy-mm-dd)
        String bookingDepDate = booking.getBooking_dep_date().toString();
        ArrayList<Room> bookingRooms = booking.getBooking_rooms();
        int bookingRoomID;
        try {
            Connection conn = new DBFactory().connect();
            String bookingRoomInsertString;
            for (Room room_i : bookingRooms) {
                bookingRoomID = room_i.getRoom_id();
                bookingRoomInsertString = ("INSERT into BOOKING_ROOM(BookingID, BookingRoomID, BookingArrDate, " +
                        "BookingDepDate) " + "VALUES ( " + bookingID + ", " + bookingRoomID + ", '" + bookingArrDate + "', '"
                        + bookingDepDate + "')");
                Statement stmtBookingRoom = conn.createStatement();
                stmtBookingRoom.executeUpdate(bookingRoomInsertString);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL addNewBooking() in BOOKING_ROOM");
            System.out.println(e.getMessage());
        }
    }


    public ObservableList<String> getLocations() {
        ObservableList<String> locations = FXCollections.observableArrayList();
        String sqlLocations = "SELECT DISTINCT HotelLocation FROM HOTEL";
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtLocations = conn.createStatement();
            ResultSet rsHotelLocation = stmtLocations.executeQuery(sqlLocations);
            while (rsHotelLocation.next()) {
                locations.add(rsHotelLocation.getString("HotelLocation"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getHotelLocations in HOTEL");
            System.out.println(e.getMessage());
        }
        return locations;
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sqlUsers = "SELECT * FROM USER";
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtUsers = conn.createStatement();
            ResultSet rsUsers = stmtUsers.executeQuery(sqlUsers);
            while (rsUsers.next()) {
                User u = new User();
                u.setUser_id(rsUsers.getInt("UserID"));
                u.setUserName(rsUsers.getString("UserName"));
                u.setEmail(rsUsers.getString("UserEmail"));
                userList.add(u);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getUsers in USER");
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public void addNewUser(String userName, String userEmail) {
        String sqlNewUserString = "INSERT INTO USER(UserName, UserEmail) VALUES ('" + userName + "', '" + userEmail + "')";
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtNewUser = conn.createStatement();
            stmtNewUser.executeUpdate(sqlNewUserString);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL addNewUser in USER");
            System.out.println(e.getMessage());
        }
    }

    public int getMaxUserID() {
        int maxUserID = 0;
        String getMaxUserIDString = "SELECT MAX(UserID) FROM USER";
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtMaxUserID = conn.createStatement();
            ResultSet rsMaxUserID = stmtMaxUserID.executeQuery(getMaxUserIDString);
            maxUserID = rsMaxUserID.getInt("MAX(UserID)");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getMaxUserID getting UserID");
            System.out.println(e.getMessage());
        }
        return maxUserID;
    }

    public int getMaxBookingID() {
        int maxBookingID = 0;
        String getMaxBookingIDString = "SELECT MAX(BookingID) FROM BOOKING";
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtMaxBookingID = conn.createStatement();
            ResultSet rsMaxBookingID = stmtMaxBookingID.executeQuery(getMaxBookingIDString);
            maxBookingID = rsMaxBookingID.getInt("MAX(BookingID)");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getMaxBookingID getting BookingID");
            System.out.println(e.getMessage());
        }
        return maxBookingID;
    }

    public ObservableList<HotelBooking> getBookingsByUserName(String userName) {
        ObservableList<HotelBooking> bookingsForUser = FXCollections.observableArrayList();
        String getUserIdByUserNameString = "SELECT * FROM USER WHERE UserName = '" + userName + "'";
        int userID = 0;
        String userEmail = "";
        User user = new User();
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtUserIDByName = conn.createStatement();
            ResultSet rsUser = stmtUserIDByName.executeQuery(getUserIdByUserNameString);
            userID = rsUser.getInt("UserID");
            userEmail = rsUser.getString("UserEmail");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getBookingsByUserName getting UserID");
            System.out.println(e.getMessage());
        }

        user.setUserName(userName);
        user.setEmail(userEmail);
        user.setUser_id(userID);

        if (userID == 0) {
            return null;
        }
        String getBookingsByUserID = "SELECT * FROM BOOKING WHERE BookingUserID = " + userID;
        int hotelID;
        try {
            Connection conn = new DBFactory().connect();
            Statement stmtBookingByUserID = conn.createStatement();
            ResultSet rsBookingByUserID = stmtBookingByUserID.executeQuery(getBookingsByUserID);
            while (rsBookingByUserID.next()) {
                HotelBooking nextBooking = new HotelBooking();
                nextBooking.setBooking_id(rsBookingByUserID.getInt("BookingID"));
                nextBooking.setBooking_user(user);
                nextBooking.setBooking_num_of_guests(rsBookingByUserID.getInt("BookingNumOfGuests"));
                hotelID = rsBookingByUserID.getInt("BookingHotelID");
                String getHotelByIDString = "SELECT * FROM HOTEL WHERE HotelID = " + hotelID;
                Statement stmtHotelByID = conn.createStatement();
                ResultSet rsHotels = stmtHotelByID.executeQuery(getHotelByIDString);
                ArrayList<Room> allRoomsForHotel = new ArrayList<>();
                Hotel h = new Hotel();
                // Set hotel id into the hotel object
                hotelID = rsHotels.getInt("hotelID");
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
                    ArrayList<LocalDate> temp = new ArrayList<>();
                    while (rsBookingRoom.next()) {
                        temp.clear();
                        arrDateString = rsBookingRoom.getString("BookingArrDate");
                        depDateString = rsBookingRoom.getString("BookingDepDate");
                        arrDate = LocalDate.parse(arrDateString);
                        depDate = LocalDate.parse(depDateString);
                        //roomOccupancy.set(i, new ArrayList<>()); // þetta gefur villu
                        temp.add(arrDate);
                        temp.add(depDate);
                        roomOccupancy.add(temp);
                        //roomOccupancy.get(i).add(arrDate);
                        //roomOccupancy.get(i).add(depDate);
                        i++;
                    }
                    r.setRoom_occupancy(roomOccupancy);

                    allRoomsForHotel.add(r);
                }
                h.setHotel_room_list(allRoomsForHotel);
                nextBooking.setBooking_hotel(h);
                String getBookingRoomsString = "SELECT * FROM BOOKING_ROOM WHERE BookingID = " + booking.getBooking_id();
                Statement stmtGetBookingRooms = conn.createStatement();
                ResultSet rsBookingRooms = stmtGetBookingRooms.executeQuery(getBookingRoomsString);
                ArrayList<Room> rooms = new ArrayList<>();
                String arrDateAsString = rsBookingRooms.getString("BookingArrDate");
                String depDateAsString = rsBookingRooms.getString("BookingDepDate");
                nextBooking.setBooking_arr_date(LocalDate.parse(arrDateAsString));
                nextBooking.setBooking_dep_date(LocalDate.parse(depDateAsString));
                while (rsBookingRooms.next()) {
                    Room r = new Room();
                    r.setRoom_id(rsBookingRooms.getInt("BookingRoomID"));
                    r.setHotel_id(hotelID);
                    String getRoomByID = "SELECT * FROM ROOM WHERE RoomID = " + rsBookingRooms.getInt("BookingRoomID");
                    Statement stmtGetRoomByID = conn.createStatement();
                    ResultSet rsRoomByID = stmtGetRoomByID.executeQuery(getRoomByID);
                    int roomCapacity = rsRoomByID.getInt("RoomCapacity");
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
                    r.setRoom_capacity(resultRC);
                    r.setRoom_category(resultRC);
                    r.setRoom_price_multiplier(rsRoomByID.getInt("RoomPriceMultiplier"));
                    String roomAmenityString = rsRoomByID.getString("RoomAmenities");
                    String[] roomAmenityStringSplitted = roomAmenityString.split(", ", 0);
                    Room.RoomAmenities[] resultRA = new Room.RoomAmenities[roomAmenityStringSplitted.length];
                    String singleRoomAmenity;
                    for (int i = 0; i < roomAmenityStringSplitted.length; i++) {
                        singleRoomAmenity = roomAmenityStringSplitted[i];
                        resultRA[i] = Room.RoomAmenities.valueOf(singleRoomAmenity);
                    }
                    r.setRoom_amenities(resultRA);
                    String sqlOccupancyForRoom = "SELECT * FROM BOOKING_ROOM WHERE BookingRoomID = " + r.getRoom_id();
                    Statement stmtOccupancyRoom = conn.createStatement();
                    ResultSet rsBookingRoom = stmtOccupancyRoom.executeQuery(sqlOccupancyForRoom);
                    String arrDateString, depDateString;
                    LocalDate arrDate, depDate;
                    ArrayList<ArrayList<LocalDate>> roomOccupancy = new ArrayList<>();
                    ArrayList<LocalDate> temp = new ArrayList<>();
                    while (rsBookingRoom.next()) {
                        temp.clear();
                        arrDateString = rsBookingRoom.getString("BookingArrDate");
                        depDateString = rsBookingRoom.getString("BookingDepDate");
                        arrDate = LocalDate.parse(arrDateString);
                        depDate = LocalDate.parse(depDateString);
                        temp.add(arrDate);
                        temp.add(depDate);
                        roomOccupancy.add(temp);
                    }
                    r.setRoom_occupancy(roomOccupancy);
                    rooms.add(r);
                }
                nextBooking.setBooking_rooms(rooms);
                bookingsForUser.add(nextBooking);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getBookingsByUserID getting Booking");
            System.out.println(e.getMessage());
        }
        return bookingsForUser;
    }

    /*
    public ArrayList<Room> getBookedRooms() {
        ArrayList<HotelBooking> allBookingsPerRoom;

        String sqlBookingRoom = "SELECT * FROM BOOKING_ROOM";

        try {
            Connection conn = new DBFactory().connect();
            Statement stmtBookingRoom = conn.createStatement();
            ResultSet rsBookingRoom = stmtBookingRoom.executeQuery(sqlBookingRoom);
            while (rsBookingRoom.next()){
                HotelBooking hb = new HotelBooking();
                hb.setBooking_id(rsBookingRoom.getInt("BookingID"));

                for (Room r : allRoomsForHotel) {

                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

     */
    /*
    public HotelBooking getBookingByMaxID() {
        HotelBooking booking = new HotelBooking();
        int bookingID = 0;
        try { // Get the auto-generated bookingID from the hotelDataBase.db file
            String sqlThisBookingID = ("SELECT MAX(BookingID) FROM BOOKING");
            Connection conn = new DBFactory().connect();
            Statement stmtThisBookingID = conn.createStatement();
            ResultSet rsBookingID = stmtThisBookingID.executeQuery(sqlThisBookingID);
            bookingID = rsBookingID.getInt("MAX(BookingID)");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error in SQL getBookingByMaxID() in getting bookingID");
            System.out.println(e.getMessage());
        }

        booking.setBooking_id(bookingID);

        try {
            String getBookingByMaxIDString = ("SELECT * FROM BOOKING WHERE BookingID = " + bookingID);
            Connection conn = new DBFactory().connect();
            Statement stmtGetBookingByMaxID = conn.createStatement();
            ResultSet rsBookingByMaxID = stmtGetBookingByMaxID.executeQuery(getBookingByMaxIDString);
            String arrDateAsString = rsBookingByMaxID.getString("BookingArrDate");
            String depDateAsString = rsBookingByMaxID.getString("BookingDepDate");
            LocalDate arrDate = LocalDate.parse(arrDateAsString);
            LocalDate depDate = LocalDate.parse(depDateAsString);
            booking.setBooking_arr_date(arrDate);
            booking.setBooking_dep_date(depDate);
            booking.setBooking_hotel();
        } catch (SQLException e) {
            System.out.println("Error in SQL getBookingByMaxID in getting booking");
            System.out.println(e.getMessage());
        }


    }
    */
    /*
    public ArrayList<HotelBooking> getBookingsPerRoom() {
        ArrayList<HotelBooking> allBookingsPerRoom;
        String sqlBookingRoom = "SELECT * FROM BOOKING_ROOM";

        try {
            Connection conn = new DBFactory().connect();
            Statement stmtBookingRoom = conn.createStatement();
            ResultSet rsBookingRoom = stmtBookingRoom.executeQuery(sqlBookingRoom);
            while (rsBookingRoom.next()){
                HotelBooking hb = new HotelBooking();
                hb.setBooking_id(rsBookingRoom.getInt("BookingID"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

     */
}
