# CodeAlpha_HotelReservationSystem

**Task 4 — Hotel Reservation System** (CodeAlpha Java Programming Internship)

A console-based, OOP hotel booking system with:
- Room search/booking by category: **Standard**, **Deluxe**, **Suite**
- Booking and cancellation of reservations
- Simulated payment processing
- Persistent storage via File I/O (Java object serialization) — rooms and
  reservations survive restarts, saved to `rooms.dat` / `reservations.dat`

## Project structure
```
CodeAlpha_HotelReservationSystem/
├── src/
│   ├── Room.java          Room entity + category enum with pricing
│   ├── Guest.java         Guest entity
│   ├── Reservation.java   Booking entity: dates, total cost, status, payment
│   ├── FileHandler.java   Save/load rooms & reservations to disk
│   ├── Hotel.java         Core business logic (search, book, cancel, pay)
│   └── Main.java          Console menu / entry point
└── README.md
```

## How to compile & run

```bash
cd CodeAlpha_HotelReservationSystem
javac -d bin src/*.java
cd bin
java Main
```

On first run, the system seeds itself with 5 Standard, 3 Deluxe and 2 Suite
rooms (room numbers 101–105, 201–203, 301–302). All data then persists to
`rooms.dat` and `reservations.dat` in the folder you ran `java Main` from.

## Menu options
1. View available rooms (optionally filtered by category)
2. Book a room (enter guest details, room number, check-in/out dates as `yyyy-MM-dd`)
3. Cancel a reservation by its ID (frees the room, marks payment as refunded if it was paid)
4. View all bookings
5. View one booking by reservation ID
6. Exit

## Notes
- Dates are entered as `yyyy-MM-dd` (e.g. `2026-12-24`).
- Payment is simulated: after booking you're asked whether to "pay" immediately;
  it always succeeds and flips the reservation's payment status to `PAID`.
- To reset all data, delete `rooms.dat` and `reservations.dat` and rerun.

## Submission checklist (per CodeAlpha instructions)
- [ ] Push this folder to GitHub as `CodeAlpha_HotelReservationSystem`
- [ ] Post a LinkedIn video walkthrough tagging @CodeAlpha, with the repo link
- [ ] Submit via the form shared in your WhatsApp group
