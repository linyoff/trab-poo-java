/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfreechart;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mvc.model.DAO.PessoaDAO;
import mvc.model.Pessoa;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author josea
 */
public class GeraRelatorio {

    public static void main(String[] args) {
        createDirectory("C:\\relatorioGerado");

        writeChartToPDF(generateBarChart(), 500, 400, "C:\\relatorioGerado\\barchart.pdf");

        writeChartToPDF(generatePieChart(), 500, 400, "C:\\relatorioGerado\\piechart.pdf");

    }

    private static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Diretório criado com sucesso: " + directoryPath);
            } else {
                System.err.println("Falha ao criar o diretório: " + directoryPath);
            }
        } else {
            System.out.println("O diretório já existe: " + directoryPath);
        }
    }

    public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) {
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height,
                    new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }

    public static JFreeChart generatePieChart() {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        List<Pessoa> pessoas = new PessoaDAO().lista(null);

        int countMasculino = 0;
        int countFeminino = 0;

        for (Pessoa p : pessoas) {
            if ("Masculino".equals(p.getSexo())) {
                countMasculino++;
            } else if ("Feminino".equals(p.getSexo())) {
                countFeminino++;
            }
        }
        dataSet.setValue("Masculino", countMasculino);
        dataSet.setValue("Feminino", countFeminino);

        JFreeChart chart = ChartFactory.createPieChart("Distribuição por Sexo", dataSet, true, true, false);

        return chart;
    }

    public static JFreeChart generateBarChart() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        
        List<Pessoa> pessoas = new PessoaDAO().lista(null);

        int countMenos18 = 0;
        int count18a30 = 0;
        int count31a50 = 0;
        int countMais50 = 0;

        for (Pessoa p : pessoas) {
            int idade = p.getIdade();

            if (idade < 18) {
                countMenos18++;
            } else if (idade >= 18 && idade <= 30) {
                count18a30++;
            } else if (idade > 30 && idade <= 50) {
                count31a50++;
            } else {
                countMais50++;
            }
        }

        dataSet.addValue(countMenos18, "Grupo de idade", "< 18");
        dataSet.addValue(count18a30, "Grupo de idade", "18-30");
        dataSet.addValue(count31a50, "Grupo de idade", "31-50");
        dataSet.addValue(countMais50, "Grupo de idade", "> 50");

        JFreeChart chart = ChartFactory.createBarChart(
                "Distribuição por grupo de idades", "Grupo de idade", "Número de Pessoas",
                dataSet, PlotOrientation.VERTICAL, false, true, false);

        return chart;
    }
}
