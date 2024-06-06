module org.mademperors.polypoly {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.mademperors.polypoly to javafx.fxml;
    exports org.mademperors.polypoly;
}