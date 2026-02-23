import java.util.List;

/**
 * SRP: Responsible ONLY for formatting the invoice text.
 * Does not compute prices or persist anything.
 */
public class InvoiceFormatter {

    public String format(String invoiceId,
                         java.util.Map<String, MenuItem> menu,
                         List<OrderLine> lines,
                         BillingCalculator.BillingSummary summary) {
        StringBuilder out = new StringBuilder();
        out.append("Invoice# ").append(invoiceId).append("\n");
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            out.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, item.price * l.qty));
        }
        out.append(String.format("Subtotal: %.2f\n", summary.subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n", summary.taxPct, summary.tax));
        out.append(String.format("Discount: -%.2f\n", summary.discount));
        out.append(String.format("TOTAL: %.2f\n", summary.total));
        return out.toString();
    }

    // kept for backward-compat (identity wrapper)
    public static String identityFormat(String s) { return s; }
}
