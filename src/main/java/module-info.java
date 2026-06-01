module tgquiz.javalab6_last {
    requires javafx.controls;
    requires javafx.fxml;


    opens tgquiz.javalab6_last to javafx.fxml;
    exports tgquiz.javalab6_last;
}