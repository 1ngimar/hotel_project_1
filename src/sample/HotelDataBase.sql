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
( RoomID INTEGER PRIMARY KEY AUTOINCREMENT
, RoomCapacity INT
, RoomPriceMultiplier real
, RoomAmenities varchar(30)
, RoomHotelID int
);

create table BOOKING
( BookingID INTEGER PRIMARY KEY AUTOINCREMENT
, BookingHotelID int
, BookingUserID int
, BookingNumOfGuests int
, BookingPaymentFinalized BOOLEAN
);

create table BOOKING_ROOM
( BookingID int
, BookingRoomID int
, BookingArrDate varchar(30)
, BookingDepDate varchar(30)
);

create table USER
( UserID INTEGER PRIMARY KEY AUTOINCREMENT
, UserName varchar(30)
, UserEmail varchar(30)
);



insert into HOTEL (HotelID, HotelName, HotelLocation, HotelAddress, HotelPostCode, HotelPhoneNumber, HotelStarRating, HotelAmenities, HotelType, HotelBasePrice)
values
                  (1, "Economy Hotel Reykjavík", "Reykjavík", "Þórunnartún 1", 105, 5550000, 3, "BREAKFAST_INCLUDED, PARKING", 1, 10000),
                  (2, "Comfort Hotel Reykjavík", "Reykjavík", "Mýrargata 2", 102, 5550001, 4, "SPA, FREE_WIFI", 2, 14000),
                  (3, "Economy Hotel Egilsstaðir", "Egilsstaðir", "Lyngás 5-7", 700, 4550000, 3, "BREAKFAST_INCLUDED, HANDICAP_ACCESSIBLE", 1, 10000),
                  (4, "Comfort Hotel Egilsstaðir", "Egilsstaðir", "Kaupvangur 17", 700, 4550001, 5, "RESTAURANT, SPA, PARKING", 2,14000),
                  (5, "Economy Hotel Akureyri", "Akureyri", "Þingvallastræti 23", 600, 4560000, 3, "FREE_WIFI", 1, 10000),
                  (6, "Comfort Hotel Akureyri", "Akureyri", "Hafnarstræti 67", 600, 4560001, 4, "RESTAURANT, PARKING, FREE_WIFI", 2, 14000),
                  (7, "Economy Hotel Ísafjörður", "Ísafjörður",  "Silfurtorgi 2", 400, 4500000, 3, "BREAKFAST_INCLUDED, PARKING", 1, 10000),
                  (8, "Comfort Hotel Ísafjörður", "Ísafjörður", "Mánagata 3", 400, 4500001, 5, "SPA, PARKING, HANDICAP_ACCESSIBLE, FREE_WIFI", 2, 23000);

insert into ROOM (RoomCapacity, RoomPriceMultiplier, RoomAmenities, RoomHotelID)
values
-- Economy Hotel Reykjavík
(1, 1, "TV", 1),
(1, 1, "TV", 1),
(1, 1, "TV", 1),
(2, 1.5, "TV, REFRIGERATOR", 1),
(2, 1.5, "TV, REFRIGERATOR", 1),
(2, 1.5, "TV, REFRIGERATOR", 1),
(4, 2, "TV, REFRIGERATOR, BALCONY", 1),
(4, 2, "TV, REFRIGERATOR, BALCONY", 1),
(4, 2, "TV, REFRIGERATOR, BALCONY", 1),
-- Comfort Hotel Reykjavík
(1, 1, "TV, BALCONY, ROOM_SERVICE", 2),
(1, 1, "TV, BALCONY, ROOM_SERVICE", 2),
(1, 1, "TV, BALCONY, ROOM_SERVICE", 2),
(2, 1.5, "TV, OCEAN_VIEW, ROOM_SERVICE", 2),
(2, 1.5, "TV, OCEAN_VIEW, ROOM_SERVICE", 2),
(2, 1.5, "TV, OCEAN_VIEW, ROOM_SERVICE", 2),
(4, 2, "TV, OCEAN_VIEW, ROOM_SERVICE", 2),
(4, 2, "TV, OCEAN_VIEW, ROOM_SERVICE", 2),
(4, 2, "TV, OCEAN_VIEW, ROOM_SERVICE", 2),
-- Economy Hotel Egilsstaðir
(1, 1, "ROOM_SERVICE", 3),
(1, 1, "ROOM_SERVICE", 3),
(1, 1, "ROOM_SERVICE", 3),
(2, 1.5, "BALCONY, ROOM_SERVICE", 3),
(2, 1.5, "BALCONY, ROOM_SERVICE", 3),
(2, 1.5, "BALCONY, ROOM_SERVICE", 3),
(4, 2, "BALCONY, ROOM_SERVICE", 3),
(4, 2, "BALCONY, ROOM_SERVICE", 3),
(4, 2, "BALCONY, ROOM_SERVICE", 3),
-- Comfort Hotel Egilsstaðir
(1, 1, "ROOM_SERVICE", 4),
(1, 1, "ROOM_SERVICE", 4),
(1, 1, "ROOM_SERVICE", 4),
(2, 1.5, "BALCONY, ROOM_SERVICE, TV", 4),
(2, 1.5, "BALCONY, ROOM_SERVICE, TV", 4),
(2, 1.5, "BALCONY, ROOM_SERVICE, TV", 4),
(4, 2, "BALCONY, ROOM_SERVICE, TV", 4),
(4, 2, "BALCONY, ROOM_SERVICE, TV", 4),
(4, 2, "BALCONY, ROOM_SERVICE, TV", 4),
-- Economy Hotel Akureyri
(1, 1, "TV, REFRIGERATOR", 5),
(1, 1, "TV, REFRIGERATOR", 5),
(1, 1, "TV, REFRIGERATOR", 5),
(2, 1.5, "TV, REFRIGERATOR, BALCONY", 5),
(2, 1.5, "TV, REFRIGERATOR, BALCONY", 5),
(2, 1.5, "TV, REFRIGERATOR, BALCONY", 5),
(4, 2, "TV, REFRIGERATOR, BALCONY", 5),
(4, 2, "TV, REFRIGERATOR, BALCONY", 5),
(4, 2, "TV, REFRIGERATOR, BALCONY", 5),
-- Comfort Hotel Akureyri
(1, 1, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(1, 1, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(1, 1, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(2, 1.5, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(2, 1.5, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(2, 1.5, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(4, 2, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(4, 2, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
(4, 2, "TV, ROOM_SERVICE, BALCONY, REFRIGERATOR", 6),
-- Economy Hotel Ísafjörður
(1, 1, "TV, OCEAN_VIEW", 7),
(1, 1, "TV, OCEAN_VIEW", 7),
(1, 1, "TV, OCEAN_VIEW", 7),
(2, 1.5, "TV, REFRIGERATOR, OCEAN_VIEW", 7),
(2, 1.5, "TV, REFRIGERATOR, OCEAN_VIEW", 7),
(2, 1.5, "TV, REFRIGERATOR, OCEAN_VIEW", 7),
(4, 2, "TV, REFRIGERATOR, OCEAN_VIEW", 7),
(4, 2, "TV, REFRIGERATOR, OCEAN_VIEW", 7),
(4, 2, "TV, REFRIGERATOR, OCEAN_VIEW", 7),
-- Comfort Hotel Ísafjörður
(1, 1, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(1, 1, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(1, 1, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(2, 1.5, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(2, 1.5, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(2, 1.5, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(4, 2, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(4, 2, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8),
(4, 2, "TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR", 8);

insert into USER values(1, "logged in user", "sample@email.com");

