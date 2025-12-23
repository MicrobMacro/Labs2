package lab6;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Shop {
    private ArrayList<Product> soldProducts = new ArrayList<>();
    public void addProduct(Product product) {
        soldProducts.add(product);
    }
    public void printProducts() {
        System.out.println("Проданные товары:");
        for (Product product : soldProducts) {
            System.out.println(product);
        }
    }
    public double getTotalSales() {
        double summa = 0;
        for (Product product : soldProducts) {
            summa += product.getPrice();
        }
        return summa;
    }
    public String getMostPopularProduct() {
        if (soldProducts.isEmpty()) {
            return "Нет продаж";
        }
        Map<String, Integer> counter = new HashMap<> ();
        for (Product product : soldProducts) {
            counter.put(product.getName(), counter.getOrDefault(product.getName(), 0) + 1);
        }
        String popular = null;
        int maCounter = 0;
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            if (entry.getValue() > maCounter) {
                maCounter = entry.getValue();
                popular = entry.getKey();
            }
        }
        return popular;
    }
}
