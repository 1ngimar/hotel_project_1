create TABLE HOTEL
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

create TABLE ROOM
( RoomID INT
, RoomCategory INT
, RoomCapacity INT
, RoomPriceMultiplier REAL
, RoomAmenities VARCHAR(30)
, RoomOccupancy VARCHAR(30)
, RoomHotelID INT
);

create TABLE BOOKING
( BookingID INT
, BookingHotelID INT
, BookingUser VARCHAR(30)
, BookingNumOfGuests INT
, BookingPaymentFinalized BOOLEAN
);

create TABLE BOOKING_ROOM
( BookingID INT
, BookingRoomID INT
, BookingArrDate VARCHAR(30)
, BookingDepDate VARCHAR(30)
);

create TABLE USER
( UserID INT
, UserName VARCHAR(30)
, UserEmail VARCHAR(30)
);

create TABLE PAYMENT
( PaymentID INT
, PaymentStatus BOOLEAN
, PaymentAmount REAL
, PaymentDate VARCHAR(30)
, PaymentCardHolderName VARCHAR(30)
, PaymentCardNumber INT
, PaymentCardExpDate VARCHAR(30)
, PaymentCardCVC INT
);

INSERT INTO HOTEL (HotelID, HotelName, HotelLocation, HotelAddress, HotelPostCode, HotelPhoneNumber, HotelStarRating, HotelAmenities, HotelType, HotelBasePrice)
VALUES 
                  (1, "Economy Hotel Reykjavík", "Reykjavík", "Þórunnartún 1", 105, 5550000, 3, "BREAKFAST_INCLUDED, PARKING", 1, 10000)
                  (2, "Comfort Hotel Reykjavík", "Reykjavík", "Mýrargata 2", 102, 5550001, 4, "SPA, FREE_WIFI", 2, 14000)
                  (3, "Economy Hotel Egilsstaðir", "Egilsstaðir", "Lyngás 5-7", 700, 4550000, 3, "BREAKFAST_INCLUDED, HANDICAP_ACCESSIBLE", 1, 10000)
                  (4, "Comfort Hotel Egilsstaðir", "Egilsstaðir", "Kaupvangur 17", 700, 4550001, 5, "RESTAURANT, SPA, PARKING", 2,14000)
                  (5, "Economy Hotel Akureyri", "Akureyri", "Þingvallastræti 23", 600, 4560000, 3, "FREE_WIFI", 1, 10000)
                  (6, "Comfort Hotel Akureyri", "Akureyri", "Hafnarstræti 67", 600, 4560001, 4, "RESTAURANT, PARKING, FREE_WIFI", 2, 14000)
                  (7, "Economy Hotel Ísafjörður", "Ísafjörður",  "Silfurtorgi 2", 400, 4500000, 3, "BREAKFAST_INCLUDED, PARKING", 1, 10000)
                  (8, "Comfort Hotel Ísafjörður", "Ísafjörður", "Mánagata 3", 400, 4500001, 5, "SPA, PARKING, HANDICAP_ACCESSIBLE, FREE_WIFI", 2, 23000)