package parking;

/**
 * SlotType represents the three physical sizes of parking slots.
 *
 * Each type carries:
 * - hourlyRate : billing rate per hour (billing is based on slot type, NOT
 * vehicle type)
 * - canFit() : whether a given vehicle type is physically compatible
 *
 * Compatibility matrix:
 * TWO_WHEELER → SMALL ✓, MEDIUM ✓, LARGE ✓
 * CAR → SMALL ✗, MEDIUM ✓, LARGE ✓
 * BUS → SMALL ✗, MEDIUM ✗, LARGE ✓
 *
 * Viva tip: The canFit() method keeps compatibility logic in one place
 * (open/closed principle). If we add VehicleType.TRUCK, we only update
 * SlotType.
 */
public enum SlotType {

    SMALL(20.0) {
        @Override
        public boolean canFit(VehicleType v) {
            return v == VehicleType.TWO_WHEELER;
        }
    },

    MEDIUM(40.0) {
        @Override
        public boolean canFit(VehicleType v) {
            return v == VehicleType.TWO_WHEELER || v == VehicleType.CAR;
        }
    },

    LARGE(80.0) {
        @Override
        public boolean canFit(VehicleType v) {
            return true; // every vehicle type fits in a large slot
        }
    };

    private final double hourlyRate;

    SlotType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    // Abstract method — each constant must implement its own rule
    public abstract boolean canFit(VehicleType vehicleType);
}
