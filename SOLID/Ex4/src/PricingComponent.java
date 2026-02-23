/**
 * OCP: Abstraction for any pricing component that contributes to the monthly fee.
 * New room types and add-ons are added by implementing this interface,
 * without editing HostelFeeCalculator.
 */
public interface PricingComponent {
    Money monthlyContribution();
}
