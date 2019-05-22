package com.mikolajewald.sklepzubraniami.aspect;


import com.mikolajewald.sklepzubraniami.raport.PdfGenaratorUtil;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Aspect
@Component
public class BeforeCreatingPdf {


    private String dirPath;

    @Before("execution(* com.mikolajewald.sklepzubraniami.controller.ItemsController.download(..))")
    public void deleteAfterReturn() {
        PdfGenaratorUtil temp = new PdfGenaratorUtil();
        dirPath = temp.raportOutputPath;
        try {
            FileUtils.cleanDirectory(new File(dirPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
