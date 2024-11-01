package io.github.carloshellschwambach.calculadorajavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculadoraController {

    @FXML
    private TextField display;

    private double valorAtual = 0;
    private String operador = "";
    private boolean novoNumero = true;

    @FXML
    private void handleButtonPress(javafx.event.ActionEvent event) {
        Button botaoPressionado = (Button) event.getSource();
        String textoBotao = botaoPressionado.getText();

        switch (textoBotao) {
            case "C":
                limparDisplay();
                break;
            case "=":
                calcular();
                novoNumero = true;
                break;
            case "+", "-", "*", "/":
                definirOperador(textoBotao);
                break;
            default:
                adicionarNumeroAoDisplay(textoBotao);
                break;
        }
    }

    private void limparDisplay() {
        display.clear();
        valorAtual = 0;
        operador = "";
        novoNumero = true;
    }

    private void definirOperador(String operador) {
        if (!display.getText().isEmpty()) {
            valorAtual = Double.parseDouble(display.getText());
        }
        this.operador = operador;
        novoNumero = true;
    }

    private void adicionarNumeroAoDisplay(String numero) {
        if (novoNumero) {
            display.clear();
            novoNumero = false;
        }
        display.appendText(numero);
    }

    private void calcular() {
        double segundoValor = display.getText().isEmpty() ? 0 : Double.parseDouble(display.getText());
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = valorAtual + segundoValor;
                break;
            case "-":
                resultado = valorAtual - segundoValor;
                break;
            case "*":
                resultado = valorAtual * segundoValor;
                break;
            case "/":
                resultado = segundoValor != 0 ? valorAtual / segundoValor : 0; // Evita divisão por zero
                break;
            default:
                resultado = segundoValor;
                break;
        }

        display.setText(String.valueOf(resultado));
        valorAtual = resultado;
        operador = "";
    }

}
