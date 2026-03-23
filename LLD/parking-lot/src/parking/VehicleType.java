package parking;

/**
 * The three categories of vehicles this parking lot supports.
 *
 * TWO_WHEELER → can use SMALL, MEDIUM, or LARGE slot
 * CAR → can use MEDIUM or LARGE slot
 * BUS → can use only LARGE slot
 *
 * (The compatibility logic lives in SlotType so the enum drives the rules.)
 */
public enum VehicleType {
    TWO_WHEELER,
    CAR,
    BUS
}
