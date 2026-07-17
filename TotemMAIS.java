package totem.mais.controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import totem.mais.TotemApplication;
import totem.mais.infrastructure.entities.Chamado;

import java.io.InputStream;
import java.time.LocalDateTime;

public class TotemMAIS extends Application {

    private ChamadoController controller;

    @Override
    public void init() {
        this.controller = TotemApplication.getSpringContext().getBean(ChamadoController.class);
    }

    @Override
    public void start(Stage primaryStage) {

        // ==========================================
        //  AUTOATENDIMENTO (TELA DO USUÁRIO)
        // ==========================================

        ImageView logoView = new ImageView();
        try {
            InputStream streamLogo = getClass().getResourceAsStream("/img/logo.png");
            if (streamLogo != null) {
                Image logo = new Image(streamLogo);
                logoView.setImage(logo);
                logoView.setFitWidth(100);
                logoView.setPreserveRatio(true);


            } else {
                System.out.println("Aviso: Imagem não encontrada em resources/img/logo.png");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao carregar a imagem do logotipo.");
        }



        Label titulo = new Label("Totem de Suporte T.I");
        titulo.getStyleClass().add("titulo");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome completo");
        txtNome.setPrefWidth(800);

        ComboBox<String> cbSetor = new ComboBox<>();
        cbSetor.getItems().addAll("RH", "Financeiro", "CallCenter", "Diretoria", "Marketing", "CPD", "Cobrança", "T.I");
        cbSetor.setPromptText("Selecione o seu setor");
        cbSetor.setPrefWidth(800);

        TextArea txtProblema = new TextArea();
        txtProblema.setPromptText("Descreva o problema de TI com detalhes...");
        txtProblema.setPrefRowCount(2);
        txtProblema.setPrefWidth(800);

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

        VBox layoutAutoatendimento = new VBox(5);
        layoutAutoatendimento.setPadding(new Insets(10, 40, 10, 40));
        layoutAutoatendimento.setAlignment(Pos.CENTER);
        layoutAutoatendimento.getChildren().addAll(
                logoView,
                titulo,
                criarLinha("Nome do Funcionário:", txtNome),
                criarLinha("Setor:", cbSetor),
                criarLinha("Problema Relatado:", txtProblema),
                new Label(""),
                btnEnviar
        );

        Tab abaAutoatendimento = new Tab("Autoatendimento");
        abaAutoatendimento.setContent(layoutAutoatendimento);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Remove o botão de fechar das abas


        // ==========================================
        // ÁREA EXCLUSIVA DE T.I.
        // ==========================================

        Tab abaTI = new Tab("Área de T.I.");
        VBox layoutTI = new VBox(15); // Aumentei o espaçamento
        layoutTI.setPadding(new Insets(20, 40, 20, 40)); // Margens laterais maiores
        layoutTI.setAlignment(Pos.TOP_CENTER); // Alinhamento ao topo central

// Título para a Área de TI
        Label tituloTI = new Label("Painel de Gestão de Chamados");
        tituloTI.getStyleClass().add("titulo"); // Reutilizando a classe do CSS
        tituloTI.setTextFill(javafx.scene.paint.Color.valueOf("#0066CC")); // Cor azul para contrastar se o fundo for branco

        TableView<Chamado> tabelaChamados = new TableView<>();
        tabelaChamados.setPrefHeight(600);

        tabelaChamados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

// colunas da Tabela com tamanhos proporcionais
        TableColumn<Chamado, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Chamado, String> colNome = new TableColumn<>("Funcionário");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));
        colNome.setPrefWidth(150);

        TableColumn<Chamado, String> colSetor = new TableColumn<>("Setor");
        colSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colSetor.setPrefWidth(100);

        TableColumn<Chamado, String> colProblema = new TableColumn<>("Problema");
        colProblema.setCellValueFactory(new PropertyValueFactory<>("problema"));
        colProblema.setPrefWidth(200);

        TableColumn<Chamado, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(100);

        TableColumn<Chamado, LocalDateTime> colData = new TableColumn<>("Data de Criação");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
        colData.setPrefWidth(150);

        tabelaChamados.getColumns().addAll(colId, colNome, colSetor, colProblema, colStatus, colData);

//   Botões de Ação abaixo da tabela
        Button btnAtualizar = new Button("Atualizar Lista");
        btnAtualizar.getStyleClass().add("botao-enviar");
        btnAtualizar.setOnAction(e -> {
            tabelaChamados.getItems().clear();
            tabelaChamados.getItems().addAll(controller.buscarChamadosAbertos());
        });

        Button btnSair = new Button("Sair (Bloquear Tela)");
        btnSair.setStyle("-fx-background-color: #CC0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 5px;");
        btnSair.setOnAction(e -> {
            tabelaChamados.getItems().clear(); // Limpa os dados por segurança
            tabPane.getSelectionModel().select(abaAutoatendimento); // Volta para a tela inicial
        });

        HBox painelBotoesTI = new HBox(15);
        painelBotoesTI.setAlignment(Pos.CENTER_RIGHT);
        painelBotoesTI.getChildren().addAll(btnAtualizar, btnSair);

        layoutTI.getChildren().addAll(tituloTI, tabelaChamados, painelBotoesTI);
        abaTI.setContent(layoutTI);

        // ==========================================
        // ESTRUTURA DO TABPANE E BLOQUEIO POR SENHA
        // ==========================================


        logoView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 5) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Acesso Restrito");
                dialog.setHeaderText("Área exclusiva para a equipe de T.I.");
                dialog.setContentText("Digite a senha de administrador:");

                dialog.showAndWait().ifPresentOrElse(senha -> {
                    if ("senha123".equals(senha)) {
                        tabelaChamados.getItems().clear();
                        tabelaChamados.getItems().addAll(controller.buscarChamadosAbertos());
                        tabPane.getSelectionModel().select(abaTI);
                    } else {
                        AlertaUtil.mostrar("ERRO", "Senha incorreta!");
                    }
                }, () -> {
                    // Clicou em cancelar
                });
            }
        });


        tabPane.getTabs().addAll(abaAutoatendimento, abaTI);

        Scene cena = new Scene(tabPane, 700, 650);

        try {
            String css = getClass().getResource("/css/style.css").toExternalForm();
            cena.getStylesheets().add(css);
        } catch (NullPointerException e) {
            System.out.println("Aviso: Arquivo CSS não encontrado.");
        }

        primaryStage.setTitle("Autoatendimento TI - MAIS");
        primaryStage.setScene(cena);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();
    }

    private VBox criarLinha(String texto, Control campo) {
        Label label = new Label(texto);
        VBox linha = new VBox(5);
        linha.setAlignment(Pos.CENTER_LEFT);
        linha.setMaxWidth(800);
        linha.getChildren().addAll(label, campo);
        return linha;
    }
}