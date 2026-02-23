public class Test {
    public static void main(String[] a) {
        String body = "Name,Score\nAyaan,82\nRiya,91\n";
        String csvBody = body.replace("\n", " ").replace(",", " ");
        String csv = "title,body\n" + "Weekly Report" + "," + csvBody + "\n";
        System.out.println("CSV length=" + csv.length());
        System.out.println("CSV=[" + csv + "]");
        String json = "{\"title\":\"Weekly Report\",\"body\":\"" + body.replace("\"", "\\\"") + "\"}";
        System.out.println("JSON length=" + json.length());
    }
}
