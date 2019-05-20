package com.mikolajewald.sklepzubraniami.raport;

import com.mikolajewald.sklepzubraniami.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class PdfGenaratorUtil {
    @Autowired
    private TemplateEngine templateEngine;
    public void createPdf(String templateName, List<Item> items) throws Exception {
        Assert.notNull(templateName, "The templateName can not be null");
        Context ctx = new Context();
        ctx.setVariable("items", items);


        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = UUID.randomUUID().toString();
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf", new File("d:/"));
            os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();
            FontResolver resolver = renderer.getFontResolver();
            renderer.getFontResolver().addFont("static/Roboto-Black.ttf", true);


            renderer.setDocumentFromString(processedHtml);

            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            System.out.println("PDF created successfully");
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
    }
}