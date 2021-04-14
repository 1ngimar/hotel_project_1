create table HOTEL
( HotelID int
, HotelName varchar(30)
, HotelLocation varchar(30)
, HotelAddress varchar(30)
, HotelPostCode int
, HotelPhoneNumber int
, HotelStarRating int
, HotelAmenities varchar(30)
, HotelType int
, HotelBasePrice int
);

create table ROOM
( RoomID int
, RoomCategory int
, RoomCapacity int
, RoomPriceMultiplier real
, RoomAmenities varchar(30)
, RoomOccupancy varchar(30)
, RoomHotelID int
);

create table BOOKING
( BookingID int
, BookingHotelID int
, BookingUserID int
, BookingNumOfGuests int
, BookingPaymentFinalized boolean
);

create table BOOKING_ROOM
( BookingID int
, BookingRoomID int
, BookingArrDate varchar(30)
, BookingDepDate varchar(30)
);

create table USER
( UserID int
, UserName varchar(30)
, UserEmail varchar(30)
);

create table PAYMENT
( PaymentID int
, PaymentStatus boolean
, PaymentAmount real
, PaymentDate varchar(30)
, PaymentCardHolderName varchar(30)
, PaymentCardNumber int
, PaymentCardExpDate varchar(30)
, PaymentCardCVC int
);

/*
INSERT INTO HOTEL (HotelID, HotelName, HotelLocation, HotelAddress, HotelPostCode, HotelPhoneNumber, HotelStarRating, HotelAmenities, HotelType, HotelBasePrice)
VALUES
                  (1, "Economy Hotel Reykjavík", "Reykjavík", "Þórunnartún 1", 105, 5550000, 3, "BREAKFAST_INCLUDED, PARKING", 1, 10000),
                  (2, "Comfort Hotel Reykjavík", "Reykjavík", "Mýrargata 2", 102, 5550001, 4, "SPA, FREE_WIFI", 2, 14000),
                  (3, "Economy Hotel Egilsstaðir", "Egilsstaðir", "Lyngás 5-7", 700, 4550000, 3, "BREAKFAST_INCLUDED, HANDICAP_ACCESSIBLE", 1, 10000),
                  (4, "Comfort Hotel Egilsstaðir", "Egilsstaðir", "Kaupvangur 17", 700, 4550001, 5, "RESTAURANT, SPA, PARKING", 2,14000),
                  (5, "Economy Hotel Akureyri", "Akureyri", "Þingvallastræti 23", 600, 4560000, 3, "FREE_WIFI", 1, 10000),
                  (6, "Comfort Hotel Akureyri", "Akureyri", "Hafnarstræti 67", 600, 4560001, 4, "RESTAURANT, PARKING, FREE_WIFI", 2, 14000),
                  (7, "Economy Hotel Ísafjörður", "Ísafjörður",  "Silfurtorgi 2", 400, 4500000, 3, "BREAKFAST_INCLUDED, PARKING", 1, 10000),
                  (8, "Comfort Hotel Ísafjörður", "Ísafjörður", "Mánagata 3", 400, 4500001, 5, "SPA, PARKING, HANDICAP_ACCESSIBLE, FREE_WIFI", 2, 23000);




*/
