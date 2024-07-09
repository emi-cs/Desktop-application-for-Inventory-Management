import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.HashMap;

//This ElectronicStoreApp is the controller, it extends Application
//there are two attributes, the Model and a HashMap storing cartContent
//the cart's data was chosen to be stored in the Controller because
// by doing so, we can prevent it from directly changing the data in the model before complete sales button is pressed
public class ElectronicStoreApp extends Application {
    private ElectronicStore model;
    private HashMap<Product, Integer> cartContent;


    //computeCartValue() method returns a string
    //this is used to compute the total amount in the cart at a given moment
    //it is used to update the cart value
    public String computeCartValue(){
        double value = 0;
        //for each item in the cart, the price*amount is calculated
        //and stored into the variable value
        for(Product items: cartContent.keySet()){
            int quantityInCart = cartContent.get(items);
            value += items.getPrice()*quantityInCart;
        }
        //this formats the value from double to a string with 2dp
        String withBrackets = "($" + String.format("%.2f", value) + "):";
        return withBrackets;
    }

    //this is another method that computes the current cart value
    //however, it returns a double
    //it is used when a sales has been completed and we would like to use this value to update the model's revenue etc
    private double currentCartValue(){
        double value = 0;
        for(Product items: cartContent.keySet()){
            int quantityInCart = cartContent.get(items);
            value += items.getPrice()*quantityInCart;
        }
        return value;
    }


    //addToCart() method takes a Product as an argument
    //it does two things -
    //1. it updates the data in the HashMap cartContent
    //2. it subtracts one from the stock quantity of a product by the setStockQuantity()
    //the if-else cases each refer to a case when the said product is not/ is already in the cartContent
    //if not, we put the new product into the HashMap
    //if there is, then we simply update the corresponding value
    private void addToCart(Product product){
        if(product.getStockQuantity() > 0){
            if(!cartContent.containsKey(product)){
                cartContent.put(product, 1);
                product.setStockQuantity(-1);
            }
            else{
                int numberOfProducts = cartContent.get(product);
                numberOfProducts+=1;
                cartContent.put(product, numberOfProducts);
                product.setStockQuantity(-1);
            }
        }
    }


    //removeFromCart() method takes a Product as an argument
    //it does two things -
    //1. it updates the data in the HashMap cartContent
    //2. it adds one from the stock quantity of a product by the setStockQuantity()
    //if-else cases refer to the 2 cases - whether removing the product will make the amount of the product in the cart turn to <=0
    //if so, we remove the data <Product, Integer> from cartContent using the HashMap default remove()
    private void removeFromCart(Product product){
        int numberOfProducts = cartContent.get(product);
        numberOfProducts-=1;
        if(numberOfProducts <= 0){
            cartContent.remove(product);
        }else{
            cartContent.put(product, numberOfProducts);
            product.setStockQuantity(1);
        }
    }


    public void start(Stage primaryStage){
        Pane aPane = new Pane();

        //initialize model
        model = ElectronicStore.createStore();

        // Create the view
        ElectronicStoreView view = new ElectronicStoreView();
        aPane.getChildren().add(view);

        //initialize the cart
        cartContent = new HashMap<Product, Integer>();




        //the title of the window is updated
        //the name of the store is appended at the back of the String "Electronic Store Application - "
        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
        view.update(model ,cartContent, computeCartValue());


        //This is an Event handler for when the mouse pressed the storeStock Pane
        //the add button will be enabled
        //update() will be called
        view.getMiddleStoreStockPane().getStoreStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                    view.getBottomButtonPane().getAddToCartButton().setDisable(false);
                    view.update(model, cartContent, computeCartValue());
                }catch(NullPointerException e){
                    //
                }
            }
        });

        //This is an Event handler for when the mouse pressed the currentCart Pane
        //the remove from cart button and the complete sale button will be enabled
        //update() will be called
        view.getRightCurrentCartPane().getCurrentCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                try {
                        view.getBottomButtonPane().getRemoveFromCartButton().setDisable(false);
                        view.getBottomButtonPane().getCompleteSaleButton().setDisable(false);
                        view.update(model, cartContent, computeCartValue());

                }catch(NullPointerException e){
                    //
                }
            }
        });



        //this is an event handler for when the add button is pressed
        //addToCart() method - residing in this controller - as defined above will be called
        //the selected product will be added to the cartContent
        //the view will be updated
        view.getBottomButtonPane().getAddToCartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try{
                    addToCart(view.getMiddleStoreStockPane().getStoreStockList().getSelectionModel().getSelectedItem());
                    view.update(model, cartContent, computeCartValue());
                }catch(NullPointerException e){
                    System.out.println("Don't press null item");
                }
            }
        });





        //Event handler for when Remove From Cart Button is pressed
        //first, the String of the selected line in the cart will be stored in cartString
        //the cartString will be compared to all the available Product's toString()
        //by this we can identify which product is in the cart
        //secondly, we pass the identified item as the argument of removeFromCart() - residing in this controller - as defined above
        //thirdly, view will be updated
        view.getBottomButtonPane().getRemoveFromCartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try{
                    Product toBeRemovedItem = null;
                    String cartString = view.getRightCurrentCartPane().getCurrentCartList().getSelectionModel().getSelectedItem();
                    for(Product item: cartContent.keySet()){
                        if(cartString.contains(item.toString())){
                            toBeRemovedItem = item;
                        }
                    }
                    try{
                        removeFromCart(toBeRemovedItem);
                    }catch(NullPointerException e){
                        //
                    }
                    view.update(model, cartContent, computeCartValue());
                }catch(NullPointerException e){
                    //
                }
            }
        });



        //Event handler for when Complete Sale Button is pressed
        //setRevenue(), increaseNumberOfSales() and updateProductSoldQuantity() will be called
        //the cartContent will be cleared
        //the view will be updated
        view.getBottomButtonPane().getCompleteSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.setRevenue(currentCartValue());
                model.increaseNumberOfSales();
                model.updateProductSoldQuantity(cartContent);
                cartContent.clear();
                view.update(model, cartContent, computeCartValue());

            }
        });

        //Event handler for when Reset Button is pressed
        //model will be a new instance of the same ElctronicStore. it will be populated by the same method createStore()
        //view will be updated
        //new cartContent will be instanciated
        //the cartValue will be set to "$(0.00)"
        view.getBottomButtonPane().getResetStoreButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try{
                    model = ElectronicStore.createStore();
                    cartContent.clear();
                    view.update(model, new HashMap<Product,Integer>(), "$(0.00)");
                }catch(NullPointerException e){
                    //
                }
            }
        });
        view.update(model, cartContent, computeCartValue());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
