//Base class for all products the store will sell
public abstract class Product implements Comparable<Product>{
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setStockQuantity(int amount){
        stockQuantity += amount;
    }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }


    //increaseSoldQuantity() method takes an integer as an input
    //the method is called by the updateProductSoldQuantity() method in the ElectronicStore class
    //it updates the soldUnit for a pqrticular product
    public void increaseSoldQuantity(int soldUnit){
        this.soldQuantity += soldUnit;
    }


    //this compareTo method returns 1 is the calling product's soldQuantity is > than the argument's
    //it is useful when determining the ranking of the most popular items of the store
    public int compareTo(Product p) {
        if (this.soldQuantity > p.soldQuantity) {
            return 1;
        } else{
            return -1;
        }
    }

}