package com.ofa.lostandfound.fileparser;

import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.exception.DocumentInvalidDataException;
import com.ofa.lostandfound.exception.DocumentNotProcessedException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PdfFileParser implements FileParser {

    @Override
    public List<LostItem> parse(MultipartFile file) throws DocumentNotProcessedException {
        List<LostItem> lostItems = new ArrayList<>();

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            String[] lines = text.split("\n");
            LostItem lostItem = null;

            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("ItemName:")) {
                    if (lostItem != null) {
                        checkValid(lostItem);
                        lostItems.add(lostItem);
                    }
                    lostItem = new LostItem();
                    lostItem.setName(line.replace("ItemName:", "").trim());
                } else if (line.startsWith("Quantity:")) {
                    if (lostItem != null) {
                        lostItem.setQuantity(Integer.parseInt(line.replace("Quantity:", "").trim()));
                    }
                } else if (line.startsWith("Place:")) {
                    if (lostItem != null) {
                        lostItem.setPlace(line.replace("Place:", "").trim());
                    }
                }
            }

            if (lostItem != null) {
                checkValid(lostItem);
                lostItems.add(lostItem);
            }
        } catch (NumberFormatException | DocumentInvalidDataException e) {
            throw new DocumentInvalidDataException();
        } catch (Exception e) {
            throw new DocumentNotProcessedException("Error parsing file: " + e.getMessage());
        }

        return lostItems;
    }

    @Override
    public Set<String> acceptedFileTypes() {
        return Set.of("pdf");
    }

}
