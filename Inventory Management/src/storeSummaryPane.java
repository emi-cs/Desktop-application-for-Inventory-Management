import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class storeSummaryPane extends Pane {
    private Text storeSummary, sales, revenue, averageDollarPerSale;
    private TextField salesTextField, revenueTextField, averageTextField;

    public storeSummaryPane(){
        Pane innerPane = new Pane();

        // Create the texts
        storeSummary = new Text("Store Summary:");
        storeSummary.relocate(20,0);

        sales = new Text("# Sales: ");
        sales.relocate(10,40);

        revenue = new Text("Revenue: ");
        revenue.relocate(0,70);

        averageDollarPerSale = new Text("$ / Sale:");
        averageDollarPerSale.relocate(9,100);



        // Create the textfields
        salesTextField = new TextField("0");
        salesTextField.setEditable(false);
        salesTextField.relocate(70, 40);
        salesTextField.setPrefSize(95, 15);
        salesTextField.setStyle("-fx-font: 15 arial;");

        revenueTextField = new TextField("0.00");
        revenueTextField.setEditable(false);
        revenueTextField.relocate(70,70);
        revenueTextField.setPrefSize(95,15);
        revenueTextField.setStyle("-fx-font: 15 arial;");

        averageTextField = new TextField("N/A");
        averageTextField.setEditable(false);
        averageTextField.relocate(70,100);
        averageTextField.setPrefSize(95,15);
        averageTextField.setStyle("-fx-font: 15 arial;");


        // Add all three buttons to the pane
        innerPane.getChildren().addAll(storeSummary, sales, revenue, averageDollarPerSale, salesTextField, revenueTextField, averageTextField);

        getChildren().addAll(innerPane);
    }

    public TextField getSalesTextField(){return salesTextField;}
    public TextField getRevenueTextField(){return revenueTextField;}
    public TextField getAverageTextField(){return averageTextField;}

    public void setSalesTextField(String s) {
        salesTextField.setText(s);
    }

    public void setRevenueTextField(String r) {
        revenueTextField.setText(r);
    }

    public void setAverageTextField(String a) {
       averageTextField.setText(a);
    }
}
