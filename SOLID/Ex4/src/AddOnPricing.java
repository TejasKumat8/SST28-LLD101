/**
 * OCP: Add-on pricing component.
 * Each add-on is its own PricingComponent.
 * Adding a new add-on = new AddOnPricing instance. Calculator unchanged.
 */
public class AddOnPricing implements PricingComponent {
    private final double fee;

    public AddOnPricing(AddOn addOn) {
        this.fee = switch (addOn) {
            case MESS    -> 1000.0;
            case LAUNDRY -> 500.0;
            case GYM     -> 300.0;
        };
    }

    @Override
    public Money monthlyContribution() { return new Money(fee); }
}
