module ni.edu.uam.equiposlaboratorio {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.equiposlaboratorio to javafx.fxml;
    exports ni.edu.uam.equiposlaboratorio;
}