package io.github.carloshellschwambach.calculadorajavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// UTILIZANDO JAVAFX
public class CalculadoraFX extends Application {

    // CRIAÇÃO DAS VARIÁVEIS DE CLASSE QUE SERÃO UTILIZADAS NO CÓDIGO
    private Label visor;
    private StringBuilder entradaAtual = new StringBuilder();
    private double primeiroOperando = 0;
    private String operador = "";
    private boolean possuiDecimal;

    public static void main(String[] args) {
        launch(args);
    }

    // INCIA A APLICAÇÃO COM O STAGE (COMO PARÂMETRO) QUE É A "JANELA" DA CALCULADORA
    @Override
    public void start(Stage primaryStage) {
        // CRIAÇÃO DO VISOR ONDE APARECERÃO OS NÚMEROS E OPERAÇÕES
        visor = new Label();
        visor.setStyle("-fx-font-size: 20px; -fx-border-color: black; -fx-padding: 10;");
        visor.setMinHeight(50);
        visor.setMaxWidth(Double.MAX_VALUE);
        visor.setAlignment(Pos.CENTER_RIGHT);

        // CRIAÇÃO DO GRID ONDE FICARÃO OS BOTÕES
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            grid.getColumnConstraints().add(col);

            RowConstraints linha = new RowConstraints();
            linha.setPercentHeight(20);
            grid.getRowConstraints().add(linha);
        }

        String[] botoes = {
                "7", "8", "9", "÷",
                "4", "5", "6", "x",
                "1", "2", "3", "-",
                "0", ",", "C", "+",
                "="
        };

        // CRIAÇÃO E ESTILIZAÇÃO DOS BOTÕES NA CALCULADORA
        int indice = 0;
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 4; coluna++) {
                String texto = botoes[indice++];
                Button botao = new Button(texto);

                if (texto.equals("÷") || texto.equals("x") || texto.equals("-") || texto.equals("+")) {
                    botao.setStyle("-fx-font-size: 18px; -fx-background-color: orange;");
                } else {
                    botao.setStyle("-fx-font-size: 18px; -fx-background-color: #9E9EFE;");
                }

                botao.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                botao.setOnAction(e -> acaoBotaoHandler(texto));
                grid.add(botao, coluna, linha);
            }
        }

        // BOTÃO IGUAL
        Button botaoIgual = new Button("=");
        botaoIgual.setStyle("-fx-font-size: 18px; -fx-background-color: #98E779");
        botaoIgual.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        botaoIgual.setOnAction(e -> acaoBotaoHandler("="));
        grid.add(botaoIgual, 0, 4, 4, 1);

        // OBJETO QUE CONTERÁ O VISOR E O GRID DA CALCULADORA
        VBox root = new VBox(10, visor, grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        VBox.setVgrow(grid, Priority.ALWAYS);

        // OBJETO ADICIONADO AO STAGE PARA MOSTRAR A CALCULADORA
        Scene scene = new Scene(root, 250, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculadora");
        primaryStage.show();
    }

    // AÇÕES DOS BOTÕES DA CALCULADORA
    private void acaoBotaoHandler(String texto) {
        switch (texto) {
            // LIMPAR
            case "C" -> {
                entradaAtual.setLength(0);
                visor.setText("");
                primeiroOperando = 0;
                operador = "";
                possuiDecimal = false;
            }
            // IGUAL
            case "=" -> calcularResultado();
            // OPERAÇÕES
            case "+", "-", "x", "÷" -> {
                if (!operador.isEmpty()) {
                    if (entradaAtual.toString().isEmpty()) {
                        visor.setText(visor.getText().replace(operador, "").trim());
                    } else {
                        calcularResultado();
                    }
                }

                operador = texto;

                if (visor.getText().equals("Divisão por zero")) {
                    visor.setText("0");
                }

                primeiroOperando = Double.parseDouble(visor.getText().replace(",", "."));
                entradaAtual.setLength(0);
                possuiDecimal = false;
                visor.setText(visor.getText() + " " + operador + " ");
            }
            // VÍRGULA
            case "," -> {
                if (!possuiDecimal) {
                    if (entradaAtual.isEmpty()) {
                        entradaAtual.append("0");
                        visor.setText(visor.getText() + "0");
                    }

                    entradaAtual.append(".");
                    visor.setText(visor.getText() + ",");
                    possuiDecimal = false;
                }
            }
            // NÚMEROS A SEREM PROCESSADOS
            default -> {
                entradaAtual.append(texto);
                visor.setText(visor.getText() + texto);
            }
        }
    }

    // CALCULA O RESULTADO DE ACORDO COM A OPERAÇÃO
    private void calcularResultado() {
        if (operador.isEmpty() || entradaAtual.isEmpty()) return;

        double segundoOperando = Double.parseDouble(entradaAtual.toString());
        double resultado;
        String resultadoStr;

        switch (operador) {
            case "+":
                resultado = primeiroOperando + segundoOperando;
                resultadoStr = String.valueOf(resultado);
                break;
            case "-":
                resultado = primeiroOperando - segundoOperando;
                resultadoStr = String.valueOf(resultado);
                break;
            case "x":
                resultado = primeiroOperando * segundoOperando;
                resultadoStr = String.valueOf(resultado);
                break;
            case "÷":
                if (segundoOperando == 0) {
                    resultado = 0;
                    resultadoStr = "Divisão por zero";
                } else {
                    resultado = primeiroOperando / segundoOperando;
                    resultadoStr = String.valueOf(resultado);
                }
                break;
            default:
                resultado = 0;
                resultadoStr = String.valueOf(resultado);
        }

        visor.setText(resultado % 1 == 0.0 ? resultadoStr.replace(".0", "") : resultadoStr.replace(".", ","));
        entradaAtual.setLength(0);
        entradaAtual.append(resultado);
        possuiDecimal = (String.valueOf(resultado).contains(","));

        operador = "";
    }

}
