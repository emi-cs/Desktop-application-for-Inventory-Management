import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ElectronicStoreView extends Pane {
        private buttonPane bottomButtonPane;
        private storeSummaryPane leftUpperStoreSummaryPane;
        private mostPopularItemsPane leftBottomMostPopularItemsPane;
        private storeStockPane middleStoreStockPane;
        private currentCartPane rightCurrentCartPane;
        private Text currentCartValue;

    public ElectronicStoreView(){
        //putting button pane in place
        bottomButtonPane  = new buttonPane();
        bottomButtonPane.relocate(35, 380);
        bottomButtonPane.setPrefSize(620,50);

        //putting summary pane in place
        leftUpperStoreSummaryPane = new storeSummaryPane();
        leftUpperStoreSummaryPane.relocate(40, 10);
        leftUpperStoreSummaryPane.setPrefSize(70,70);

        //putting most popular pane in place
        leftBottomMostPopularItemsPane = new mostPopularItemsPane();
        leftBottomMostPopularItemsPane.relocate(15,150);
        leftBottomMostPopularItemsPane.setPrefSize(80,80);

        //putting stock pane in place
        middleStoreStockPane = new storeStockPane();
        middleStoreStockPane.relocate(240,10);
        middleStoreStockPane.setPrefSize(310,310);

        //putting cart pane in place
        rightCurrentCartPane = new currentCartPane();
        rightCurrentCartPane.relocate(550,10);
        rightCurrentCartPane.setPrefSize(310,310);

        //putting currentCartValue in place
        currentCartValue = new Text();
        currentCartValue.relocate(700,10);


        getChildren().addAll(bottomButtonPane,leftUpperStoreSummaryPane,leftBottomMostPopularItemsPane, middleStoreStockPane, rightCurrentCartPane, currentCartValue);

        setPrefSize(900, 450);
    }

    //the update method takes 3 arguments
    //model, the cartContent that resides in the controller as a HashMap, and the String that represents the cartValue
    public void update(ElectronicStore model, HashMap<Product, Integer> cartContent, String cartValue) {
        //update store stock
        ArrayList<Product> storeStockList = new ArrayList<Product>();
        Product[] listOfStock = model.getStock();
        //for each item in the listOfStock of the store, if the item is null or the item's stock quantity, do nothing
        //else, we add it to the ArrayList storeStockList
        for(Product item: listOfStock){
            if(item == null || item.getStockQuantity() == 0){
                //do nothing
            }else{
                storeStockList.add(item);
            }
        }
        //the ArrayList storeStockList is then set to be the content of middleStoreStockPane- StoreStockList
        middleStoreStockPane.getStoreStockList().setItems((FXCollections.observableArrayList(storeStockList)));


        //update cart view
        //for each item in the cartCotent
        //put it in the cartProductList with formatting so that the amount of the product and the product description will be mentioned in the cartProductList
        ArrayList<String> cartProductList = new ArrayList<String>();
        for(Product cartItems: cartContent.keySet()){
            String description = cartContent.get(cartItems).toString() + " x "+ cartItems.toString();
            cartProductList.add(description);
        }
        //cartProductList is then set to be the content of current cart list
        rightCurrentCartPane.getCurrentCartList().setItems((FXCollections.observableArrayList(cartProductList)));



        //update current cart pane
        currentCartValue.setText(cartValue);


        //update #sales and revenue and $/Sale
        leftUpperStoreSummaryPane.setSalesTextField(String.valueOf(model.getNumberOfSales()));
        leftUpperStoreSummaryPane.setRevenueTextField(String.format("%.2f",model.getRevenue()));
        if(model.getRevenue() == 0){
            //if the revenue is 0, it defaults to NaN because we cannot divide 0
            //so we write "N/A" in the textfield
            leftUpperStoreSummaryPane.setAverageTextField("N/A");
        }
        else if(model.getRevenue() > 0){
            //if the revenue is > 0, we can get the average Revenu and format and put it in the textfield
            leftUpperStoreSummaryPane.setAverageTextField(String.format("%.2f", model.getAverageRevenue()));
        }
        else{
            //
        }


        //when cart is empty - remove from cart AND complete sale is disabled
        if(cartContent.isEmpty()){
            getBottomButtonPane().getCompleteSaleButton().setDisable(true);
            getBottomButtonPane().getRemoveFromCartButton().setDisable(true);
        }

        //when cart is not empty - complete sale can be pressed
        if(!cartContent.isEmpty()){
            getBottomButtonPane().getCompleteSaleButton().setDisable(false);
        }


        //bottomButtonPane.getRemoveFromCartButton().setDisable(getRightCurrentCartPane().getCurrentCartList().getSelectionModel().getSelectedIndex()<0);
        //bottomButtonPane.getAddToCartButton().setDisable(getMiddleStoreStockPane().getStoreStockList().getSelectionModel().getSelectedIndex()<0);


        //populate the mostPopular list
        List<Product> productList = new ArrayList<Product>();
        List<Product> top3productList = new ArrayList<Product>();
        //for each product in stock
        //we add it into the ArrayList productList
        for(Product product: model.getStock()){
            if(product == null){
                //do nothing
            }
            else{
                productList.add(product);
            }
        }
        //this sorts the productList in order
        Collections.sort(productList);
        //this reverse the productList so that it is in the right ascending order
        Collections.reverse(productList);
        //we add the top3 in the productList into top3productList
        for (int i =0 ; i < 3 ; i++){
            top3productList.add(productList.get(i));
        }
        //data in the top3productList is set as the data to be displayed in the most popular list
        leftBottomMostPopularItemsPane.getMostPopularList().setItems((FXCollections.observableArrayList(top3productList)));




        //this determines if the Remove from Cart button is enabled/ disabled
        //it is set to enabled when the selected item's index of the current cart is >=0
        //it is set to disabled when the selected item's index of the current cart is < 0 (i.e. when there's nothing in the cart)
        if(rightCurrentCartPane.getCurrentCartList().getSelectionModel().getSelectedIndex() >= 0){
            bottomButtonPane.getRemoveFromCartButton().setDisable(false);
        }else{
            bottomButtonPane.getRemoveFromCartButton().setDisable(true);
        }

        //this determines if the add to cart button is enabled/ disabled
        //it is set to enabled when the selected item's index of the stock is >=0
        //it is set to disabled when the selected item's index of the stock is < 0 (i.e. when nothing in the stock pane is selected)
        if(middleStoreStockPane.getStoreStockList().getSelectionModel().getSelectedIndex() >= 0){
            bottomButtonPane.getAddToCartButton().setDisable(false);
        }else{
            bottomButtonPane.getAddToCartButton().setDisable(true);
        }


    }

    public buttonPane getBottomButtonPane() {
        return bottomButtonPane;
    }

    public storeStockPane getMiddleStoreStockPane(){
        return middleStoreStockPane;
    }

    public currentCartPane getRightCurrentCartPane(){
        return rightCurrentCartPane;
    }
}
