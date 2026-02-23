import java.util.List;

/**
 * SRP: Responsible ONLY for computing pricing, tax and discount
 * for a billing session. No formatting, no persistence.
 */
public class BillingCalculator {

    /**
     * Encapsulates a computed invoice amount breakdown.
     */
    public static class BillingSummary {
        public final double subtotal;
        public final double taxPct;
        public final double tax;
        public final double discount;
        public final double total;

        public BillingSummary(double subtotal, double taxPct, double tax, double discount, double total) {
            this.subtotal = subtotal;
            this.taxPct   = taxPct;
            this.tax      = tax;
            this.discount = discount;
            this.total    = total;
        }
    }

    public BillingSummary compute(String customerType, java.util.Map<String, MenuItem> menu, List<OrderLine> lines) {
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            subtotal += item.price * l.qty;
        }
        double taxPct  = TaxRules.taxPercent(customerType);
        double tax     = subtotal * (taxPct / 100.0);
        double discount = DiscountRules.discountAmount(customerType, subtotal, lines.size());
        double total   = subtotal + tax - discount;
        return new BillingSummary(subtotal, taxPct, tax, discount, total);
    }
}
