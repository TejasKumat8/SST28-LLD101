/**
 * OCP: Room pricing component.
 * Maps a LegacyRoomTypes constant to its base monthly fee.
 * Adding a new room type = new RoomPricing instance; no switch needed.
 */
public class RoomPricing implements PricingComponent {
    private final double baseMonthly;

    public RoomPricing(int roomType) {
        this.baseMonthly = switch (roomType) {
            case LegacyRoomTypes.SINGLE -> 14000.0;
            case LegacyRoomTypes.DOUBLE -> 15000.0;
            case LegacyRoomTypes.TRIPLE -> 12000.0;
            default                     -> 16000.0; // DELUXE
        };
    }

    @Override
    public Money monthlyContribution() { return new Money(baseMonthly); }
}
