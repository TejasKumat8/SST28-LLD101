import java.util.*;

/**
 * SRP (Refactored):
 * CafeteriaSystem now ONLY orchestrates the checkout workflow.
 *   - menu management (addToMenu)
 *   - delegates pricing  → BillingCalculator
 *   - delegates formatting → InvoiceFormatter
 *   - delegates persistence → FileStore
 *
 * It does NOT format strings, encode tax rates, or contain discount specifics.
 */
public class CafeteriaSystem {

    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final FileStore store = new FileStore();
    private final BillingCalculator calculator = new BillingCalculator();
    private final InvoiceFormatter formatter = new InvoiceFormatter();
    private int invoiceSeq = 1000;

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        BillingCalculator.BillingSummary summary = calculator.compute(customerType, menu, lines);
        String invoiceText = formatter.format(invId, menu, lines, summary);

        System.out.print(invoiceText);

        store.save(invId, invoiceText);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
