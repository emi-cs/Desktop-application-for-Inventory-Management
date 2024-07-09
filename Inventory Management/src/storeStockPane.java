import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class storeStockPane extends Pane {
    private Text storeStock;
    private ListView<Product>  storeStockList;

    public storeStockPane(){
        Pane innerPane = new Pane();

        // Create the texts
        storeStock = new Text("Store Stock:");
        storeStock.relocate(100,0);


        // Create the ListView
        storeStockList = new ListView<>();
        storeStockList.relocate(0, 40);
        storeStockList.setPrefSize(300,320);

        // Add all three buttons to the pane
        innerPane.getChildren().addAll(storeStock,storeStockList);

        getChildren().addAll(innerPane);
    }

    public ListView<Product> getStoreStockList(){return storeStockList;}

}