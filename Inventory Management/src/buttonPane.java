import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

//this buttonPane stores the 4 buttons in the application
public class buttonPane extends Pane {
    private Button resetStoreButton, addToCartButton, removeFromCartButton, completeSaleButton;

    public buttonPane(){
        Pane innerPane = new Pane();

        // Create the buttons
        resetStoreButton = new Button("Reset Store");
        resetStoreButton.relocate(0, 0);
        resetStoreButton.setPrefSize(155,55);
        resetStoreButton.setDisable(false);

        addToCartButton = new Button("Add to Cart");
        addToCartButton.relocate(270, 0);
        addToCartButton.setPrefSize(155,55);
        addToCartButton.setDisable(true);

        removeFromCartButton = new Button("Remove from Cart");
        removeFromCartButton.relocate(520, 0);
        removeFromCartButton.setPrefSize(155,55);
        removeFromCartButton.setDisable(true);

        completeSaleButton = new Button("Complete Sale");
        completeSaleButton.relocate(675, 0);
        completeSaleButton.setPrefSize(155,55);
        completeSaleButton.setDisable(true);

        // Add all three buttons to the pane
        innerPane.getChildren().addAll(resetStoreButton, addToCartButton, removeFromCartButton,completeSaleButton);

        getChildren().addAll(innerPane);
    }

    public Button getResetStoreButton(){return resetStoreButton;}
    public Button getAddToCartButton(){return addToCartButton;}
    public Button getRemoveFromCartButton(){return removeFromCartButton;}
    public Button getCompleteSaleButton(){return completeSaleButton;}

}
