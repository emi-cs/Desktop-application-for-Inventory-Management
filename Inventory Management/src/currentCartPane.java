import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;



public class currentCartPane extends Pane {
    private Text currentCart;
    private ListView<String> currentCartList;


    public currentCartPane(){
        Pane innerPane = new Pane();

        // Create the texts
        currentCart = new Text("Current Cart ");
        currentCart.relocate(60,0);


        // Create the ListView
        currentCartList = new ListView<>();
        currentCartList.relocate(0, 40);
        currentCartList.setPrefSize(320,320);

        // Add all three buttons to the pane
        innerPane.getChildren().addAll(currentCart,currentCartList);

        getChildren().addAll(innerPane);
    }

    public ListView<String> getCurrentCartList(){return currentCartList;}







}