package com.mikolajewald.sklepzubraniami.raport;

import com.mikolajewald.sklepzubraniami.entity.Item;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class PdfGenaratorUtil {

    public String raportOutputPath = "./raporty/";
    private String fontPath = "./resources/ARIALUNI.TTF";


    private TemplateEngine templateEngine;

    @Autowired
    public PdfGenaratorUtil(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public PdfGenaratorUtil() {

    }

    public Path upgradedCreatePdf(String templateName, List<Item> items) throws Exception {
        Assert.notNull(templateName, "The templateName can not be null");
        Context ctx = new Context();
        ctx.setVariable("items", items);

        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = "raport";
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf", new File(raportOutputPath));
            os = new FileOutputStream(outputFile);

            PdfRendererBuilder builder = new PdfRendererBuilder();
            Document doc = Jsoup.parse(processedHtml);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), outputFile.getAbsolutePath())
                    .useFont(new File(fontPath), "Arial Unicode MS")
                    .toStream(os)
                    .run();

            return Paths.get(outputFile.getAbsolutePath());
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
    }
}