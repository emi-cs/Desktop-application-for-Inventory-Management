import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class mostPopularItemsPane extends Pane {
    private Text mostPopularItems;
    private ListView<Product> mostPopularList;

    public mostPopularItemsPane(){
        Pane innerPane = new Pane();

        // Create the texts
        mostPopularItems = new Text("Most Popular Items:");
        mostPopularItems.relocate(25,10);


        // Create the ListView
        mostPopularList = new ListView<>();
        mostPopularList.relocate(0, 35);
        mostPopularList.setPrefSize(190,185);


        // Add all three buttons to the pane
        innerPane.getChildren().addAll(mostPopularItems,mostPopularList);

        getChildren().addAll(innerPane);
    }

    public ListView<Product> getMostPopularList(){return mostPopularList;}

}