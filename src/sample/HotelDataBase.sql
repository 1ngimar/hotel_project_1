CREATE TABLE HOTEL
( HotelID INT
, HotelName VARCHAR(30)
, HotelLocation VARCHAR(30)
, HotelAddress VARCHAR(30)
, HotelPostCode INT
, HotelPhoneNumber INT
, HotelStarRating INT
, HotelAmenities VARCHAR(30)
, HotelType INT
, HotelBasePrice INT   
);

CREATE TABLE ROOM
( RoomID INT
, RoomCategory INT
, RoomCapacity INT
, RoomPriceMultiplier REAL
, RoomAmenities VARCHAR(30)
, RoomOccupancy VARCHAR(30)
, RoomHotelID INT
);

CREATE TABLE BOOKING
( BookingID INT
, BookingHotelID INT
, BookingUser VARCHAR(30)
, BookingArrDate VARCHAR(30)
, BookingDepDate VARCHAR(30)
, BookingRooms VARCHAR(30)
, BookingNumOfGuests INT
, BookingPaymentFinalized BOOLEAN
);

CREATE TABLE USER
( UserID INT
, UserName VARCHAR(30)
, UserEmail VARCHAR(30)
);

CREATE TABLE PAYMENT
( PaymentID INT
, PaymentStatus BOOLEAN
, PaymentAmount REAL
, PaymentDate VARCHAR(30)
, PaymentCardHolderName VARCHAR(30)
, PaymentCardNumber INT
, PaymentCardExpDate VARCHAR(30)
, PaymentCardCVC INT
)
