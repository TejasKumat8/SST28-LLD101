# Multilevel Parking Lot — LLD Implementation

## Overview

A complete object-oriented implementation of a **Multilevel Parking Lot** system in Java.

Supports:
- 3 slot sizes: **SMALL** (₹20/hr), **MEDIUM** (₹40/hr), **LARGE** (₹80/hr)
- 3 vehicle types: **Two-Wheeler**, **Car**, **Bus**
- Multiple floors and multiple entry gates
- **Nearest-slot assignment** based on entry gate
- **Slot upgrade** when the preferred size is full
- **Billing based on slot type** (not vehicle type)
- Ceiling-hour billing (partial hour = full hour charged)

---

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ParkingLot (Singleton)                             │
│                                                                              │
│  - floors       : List<ParkingFloor>                                         │
│  - gates        : Map<String, EntryGate>                                     │
│  - activeTickets: Map<String, ParkingTicket>                                 │
│  ─────────────────────────────────────────────────────────────────────────── │
│  + getInstance()  : ParkingLot                                               │
│  + addFloor(ParkingFloor)                                                    │
│  + addGate(EntryGate)                                                        │
│  + park(vehicle, entryTime, requestedSlotType, entryGateId) : ParkingTicket  │
│  + status()       : void                                                     │
│  + exit(ticketId, exitTime) : Bill                                           │
│  - upgradeOrder(vehicleType, requested) : List<SlotType>                     │
│  - findNearestSlot(vehicleType, slotType, gate) : ParkingSlot                │
└───────┬─────────────────┬──────────────────────────────────────────────────┘
        │ has many        │ has many
        ▼                 ▼
┌───────────────┐   ┌────────────┐
│ ParkingFloor  │   │ EntryGate  │
│ ──────────── │   │ ─────────  │
│ - floorNumber │   │ - gateId   │
│ - slots       │   │ - floorNum │
│ + addSlot()   │   └────────────┘
│ + countAvail()│
└──────┬────────┘
       │ has many
       ▼
┌────────────────────────────────────────┐
│               ParkingSlot              │
│  - slotId, slotType                    │
│  - floorNumber, slotIndex              │
│  - occupied : boolean                  │
│  + isAvailableFor(VehicleType) : bool  │
│  + occupy() / vacate()                 │
└────────────────────────────────────────┘

┌──────────────┐     ┌──────────────────┐
│   Vehicle    │     │  ParkingTicket   │
│ ──────────── │     │ ──────────────── │
│ - number     │     │ - ticketId (UUID)│
│ - vehicleType│     │ - vehicle        │
└──────────────┘     │ - slot           │
                     │ - entryTime      │
                     │ - entryGateId    │
                     └────────┬─────────┘
                              │ used by
                              ▼
                     ┌──────────────────┐
                     │      Bill        │
                     │ ──────────────── │
                     │ - ticket         │
                     │ - exitTime       │
                     │ - durationMins   │
                     │ - totalAmount    │
                     └──────────────────┘

┌─────────────────────────────────────┐   ┌───────────────────┐
│            SlotType (enum)          │   │ VehicleType (enum) │
│  SMALL  (₹20/hr) canFit: 2W only   │   │  TWO_WHEELER       │
│  MEDIUM (₹40/hr) canFit: 2W, CAR   │   │  CAR               │
│  LARGE  (₹80/hr) canFit: all       │   │  BUS               │
│  + canFit(VehicleType) : bool       │   └───────────────────┘
└─────────────────────────────────────┘
```

---

## Design Decisions & Viva Q&A

| Question | Answer |
|---|---|
| **Why Singleton for ParkingLot?** | There is only one physical lot. Singleton ensures one shared state for slot availability, active tickets, and floors. No two parts of the code can hold different "views" of the lot. |
| **How does nearest-slot work?** | Every slot has `(floorNumber, slotIndex)`. Every gate has a `floorNumber`. Distance = `\|gateFloor − slotFloor\| × 1000 + slotIndex`. A weight of 1000 ensures same-floor slots always beat other-floor slots. Within the same floor, the smallest `slotIndex` (physically closest to the gate) wins. |
| **Why `canFit()` in SlotType (not VehicleType)?** | Open/Closed principle — if you add a new vehicle type (e.g., TRUCK), you only change `SlotType.canFit()`. No scattered if-else in the main logic. |
| **Why HashMap for activeTickets?** | O(1) lookup by ticketId at exit time. Much faster than scanning a list. |
| **Billing on slot type, not vehicle type?** | A bike in a MEDIUM slot pays the MEDIUM rate. `Bill` reads `ticket.getSlot().getSlotType().getHourlyRate()` — the slot carries the rate, not the vehicle. |
| **How is slot upgrade done?** | `upgradeOrder()` builds the list `[requested, next, …]` filtering to types where `canFit()` is true for the vehicle. `park()` tries each in order until it finds a free slot. |
| **Why ceiling-hour billing?** | `hoursCharged = (durationMinutes + 59) / 60`. Partial hours count as a full hour — this is the industry norm and protects revenue. |
| **How do you prevent a slot from being double-assigned?** | `ParkingSlot.isAvailableFor()` checks `!occupied`. `findNearestSlot` only returns a free slot. `occupy()` is called immediately after assignment. In a concurrent system we'd add `synchronized` here. |

---

## Slot Compatibility Matrix

| Vehicle Type  | SMALL | MEDIUM | LARGE |
|---------------|-------|--------|-------|
| TWO_WHEELER   | ✓     | ✓      | ✓     |
| CAR           | ✗     | ✓      | ✓     |
| BUS           | ✗     | ✗      | ✓     |

**Billing rate follows the slot, not the vehicle.**  
If a TWO_WHEELER parks in MEDIUM → charged ₹40/hr.

---

## How to Run

```bash
# Compile from the parking-lot directory
javac -d out src/parking/*.java

# Run
java -cp out parking.Main
```

---

## File Structure

```
parking-lot/
└── src/
    └── parking/
        ├── Main.java             ← demo scenarios
        ├── ParkingLot.java       ← Singleton core controller
        ├── ParkingFloor.java     ← groups slots by floor
        ├── ParkingSlot.java      ← individual slot entity
        ├── EntryGate.java        ← gate with floor reference
        ├── Vehicle.java          ← vehicle data object
        ├── ParkingTicket.java    ← issued at entry
        ├── Bill.java             ← generated at exit
        ├── SlotType.java         ← enum with rate + canFit()
        └── VehicleType.java      ← enum of vehicle categories
```

---

## Hourly Rates

| Slot Type | Rate    |
|-----------|---------|
| SMALL     | ₹20/hr  |
| MEDIUM    | ₹40/hr  |
| LARGE     | ₹80/hr  |
