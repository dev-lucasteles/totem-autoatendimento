package totem.mais.controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import totem.mais.TotemApplication;
import totem.mais.infrastructure.entities.Chamado;
import java.io.InputStream;

public class TotemMAIS extends Application {

    private ChamadoController controller;

    @Override
    public void init() {
        this.controller = TotemApplication.getSpringContext().getBean(ChamadoController.class);
    }

    @Override
    public void start(Stage primaryStage) {
        ImageView logoView = new ImageView();
        try {
            // Procura a imagem dentro da pasta resources/img
            InputStream streamLogo = getClass().getResourceAsStream("/img/logo.png");
            if (streamLogo != null) {
                Image logo = new Image(streamLogo);
                logoView.setImage(logo);
                logoView.setFitWidth(180); // Ajuste a largura do logo aqui
                logoView.setPreserveRatio(true); // Mantém as proporções originais
            } else {
                System.out.println("Aviso: Imagem não encontrada em resources/img/logo.png");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao carregar a imagem do logotipo.");
        }

        // Título
        Label titulo = new Label("Totem de Suporte T.I - MAIS");
        titulo.getStyleClass().add("titulo");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome completo");
        txtNome.setPrefWidth(400);

        ComboBox<String> cbSetor = new ComboBox<>();
        cbSetor.getItems().addAll("RH", "Financeiro", "CallCenter", "Diretoria", "Marketing", "CPD", "Cobranças");
        cbSetor.setPromptText("Selecione o seu setor");
        cbSetor.setPrefWidth(400);

        TextArea txtProblema = new TextArea();
        txtProblema.setPromptText("Descreva o problema de TI com detalhes...");
        txtProblema.setPrefRowCount(5);
        txtProblema.setPrefWidth(400);

        // Botão
        Button btnEnviar = new Button("ABRIR CHAMADO");
        btnEnviar.getStyleClass().add("botao-enviar");

        btnEnviar.setOnAction(e -> {
            String nome = txtNome.getText();
            String setor = cbSetor.getValue();
            String problema = txtProblema.getText();

            try {
                Chamado chamadoSalvo = controller.abrirChamado(nome, setor, problema);
                ImpressoraUtil.imprimirTicket(chamadoSalvo);
                
                AlertaUtil.mostrar("Sucesso", "Chamado #" + chamadoSalvo.getId() + " aberto com sucesso!\nRetire o seu ticket.");

                txtNome.clear();
                cbSetor.setValue(null);
                txtProblema.clear();
                
            } catch (IllegalArgumentException erro) {
                AlertaUtil.mostrar("Aviso do Sistema", erro.getMessage());
            } catch (Exception erroGeral) {
                AlertaUtil.mostrar("Erro Grave", "ERRO: Falha ao processar o chamado.\n" + erroGeral.getMessage());
            }
        });

        // Layout Principal
        VBox layout = new VBox(10); 
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        
        // Colocando os itens na tela (Agora o logoView entra primeiro!)
        layout.getChildren().addAll(
            logoView, 
            titulo, 
            criarLinha("Nome do Funcionário:", txtNome), 
            criarLinha("Setor:", cbSetor), 
            criarLinha("Problema Relatado:", txtProblema), 
            new Label(""), // Espaçador invisível
            btnEnviar
        );

        Scene cena = new Scene(layout, 700, 650);
        
        // Carrega o CSS
        String css = getClass().getResource("/css/style.css").toExternalForm();
        cena.getStylesheets().add(css);

        primaryStage.setTitle("Autoatendimento TI - MAIS");
        primaryStage.setScene(cena);
        primaryStage.show();
    }

    private VBox criarLinha(String texto, Control campo) {
        Label label = new Label(texto);
        VBox linha = new VBox(5);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setMaxWidth(400);
        linha.getChildren().addAll(label, campo);
        return linha;
    }
}