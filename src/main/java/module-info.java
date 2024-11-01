module io.github.carloshellschwambach.calculadorajavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.carloshellschwambach.calculadorajavafx to javafx.fxml;
    exports io.github.carloshellschwambach.calculadorajavafx;
}