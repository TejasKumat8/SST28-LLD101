package parking;

/**
 * Simple data object representing a vehicle.
 *
 * vehicleNumber : licence plate, unique identifier
 * vehicleType : determines which slot sizes are compatible
 */
public class Vehicle {

    private final String vehicleNumber;
    private final VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return vehicleType + " [" + vehicleNumber + "]";
    }
}
