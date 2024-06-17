module org.mademperors.polypoly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens org.mademperors.polypoly to javafx.fxml;
    exports org.mademperors.polypoly;
    exports org.mademperors.polypoly.controllers;
    opens org.mademperors.polypoly.controllers to javafx.fxml;
    exports org.mademperors.polypoly.models;
    opens org.mademperors.polypoly.models to javafx.fxml;
    exports org.mademperors.polypoly.listeners;
    opens org.mademperors.polypoly.listeners to javafx.fxml;
    exports org.mademperors.polypoly.controllers.serviceCardsControllers;
    opens org.mademperors.polypoly.controllers.serviceCardsControllers to javafx.fxml;
}