package lab6;
public class MainForShop {
    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.addProduct(new Product("ddr5", 999999));
        shop.addProduct(new Product("thick water", 160));
        shop.addProduct(new Product("немолоко", 140));
        shop.addProduct(new Product("кефир", 90));
        shop.addProduct(new Product("немолоко", 160));
        shop.printProducts();
        System.out.println("Сумма продаваемых продуктов = " + shop.getTotalSales() + " руб.");
        System.out.println("Больше всего товара: " + shop.getMostPopularProduct());
    }

}
