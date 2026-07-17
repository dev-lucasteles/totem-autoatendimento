package totem.mais.controller;

import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import totem.mais.infrastructure.entities.Chamado;

public class ImpressoraUtil {

    public static void imprimirTicket(Chamado chamado) {
        PrinterJob job = PrinterJob.createPrinterJob();
        
        if (job != null) {
            Printer impressora = job.getPrinter();


            // Isso remove o espaço em branco gigante no topo e na esquerda
            PageLayout pageLayout = impressora.createPageLayout(
                    impressora.getDefaultPageLayout().getPaper(),
                    PageOrientation.PORTRAIT,
                    0, 0, 0, 0 // Margens: Esquerda, Direita, Topo, Baixo
            );

            VBox ticket = new VBox(5);
            ticket.setStyle("-fx-padding: 5px; -fx-background-color: white;");
            
            // 2. Lê a largura exata da bobina e trava o ticket nela
            double larguraBobina = pageLayout.getPrintableWidth();
            ticket.setPrefWidth(larguraBobina);
            ticket.setMaxWidth(larguraBobina);
            
            // 3. Textos (O setMaxWidth obriga o texto a quebrar a linha se for grande)
            Label cabecalho = new Label("--- SUPORTE T.I MAIS ---");
            cabecalho.setFont(Font.font("Monospaced", FontWeight.BOLD, 12));
            cabecalho.setWrapText(true);
            cabecalho.setMaxWidth(larguraBobina);
            
            Label lblNumero = new Label("TICKET: #" + chamado.getId());
            lblNumero.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
            lblNumero.setWrapText(true);
            lblNumero.setMaxWidth(larguraBobina);
            
            Label lblNome = new Label("Funcionario: " + chamado.getNomeFuncionario());
            lblNome.setFont(Font.font("Monospaced", FontWeight.BOLD, 11));
            lblNome.setWrapText(true);
            lblNome.setMaxWidth(larguraBobina);
            
            Label lblSetor = new Label("Setor: " + chamado.getSetor());
            lblSetor.setFont(Font.font("Monospaced", FontWeight.BOLD , 11));
            lblSetor.setWrapText(true);
            lblSetor.setMaxWidth(larguraBobina);
            
            Label lblAviso = new Label("\nAguarde, a equipe de T.I\nchamará em breve.");
            lblAviso.setFont(Font.font("Monospaced", FontWeight.BOLD, 11));
            lblAviso.setWrapText(true);
            lblAviso.setMaxWidth(larguraBobina);
            
            ticket.getChildren().addAll(cabecalho, lblNumero, lblNome, lblSetor, lblAviso);

            // 4. Imprime PASSANDO O NOVO LAYOUT sem margens
            boolean sucesso = job.printPage(pageLayout, ticket);
            
            if (sucesso) {
                job.endJob();
            } else {
                System.out.println("Erro: Não foi possível enviar para a impressora.");
            }
        } else {
            System.out.println("Erro: Nenhuma impressora instalada.");
        }
    }
}