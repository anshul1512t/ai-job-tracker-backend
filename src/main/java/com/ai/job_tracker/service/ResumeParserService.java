package com.ai.job_tracker.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.job_tracker.exception.EmptyFileException;
import com.ai.job_tracker.exception.InvalidFileFormatException;

import java.io.IOException;

@Service
public class ResumeParserService {

    public String parse(MultipartFile file) throws IOException {

        String contentType = file.getContentType();

        if (contentType == null) {
            throw new EmptyFileException("File type is null");
        }

        if (contentType.equals("application/pdf")) {

            try (PDDocument document = Loader.loadPDF(file.getBytes())) {

                PDFTextStripper stripper = new PDFTextStripper();

                String text = stripper.getText(document);

                return cleanText(text);
            }
        }

        else if (contentType.equals(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {

            try (
                    XWPFDocument document = new XWPFDocument(file.getInputStream());

                    XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

                String text = extractor.getText();

                return cleanText(text);
            }
        }

        throw new InvalidFileFormatException("Only PDF and DOCX supported");
    }

    // Optional cleanup for AI
    private String cleanText(String text) {

        return text
                .replaceAll("\\p{Cntrl}", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}