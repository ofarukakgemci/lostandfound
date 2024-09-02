package com.ofa.lostandfound.fileparser;

import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.exception.DocumentInvalidDataException;
import com.ofa.lostandfound.exception.DocumentNotProcessedException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("text")
public class TextFileParser implements FileParser {

    @Override
    public List<LostItem> parse(MultipartFile file) throws DocumentNotProcessedException {
        List<LostItem> lostItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            LostItem lostItem = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ItemName:")) {
                    if (lostItem != null) {
                        checkValid(lostItem);
                        lostItems.add(lostItem);
                    }
                    lostItem = new LostItem();
                    lostItem.setName(line.substring(9).trim());
                } else if (line.startsWith("Quantity:")) {
                    if (lostItem != null) {
                        lostItem.setQuantity(Integer.parseInt(line.substring(9).trim()));
                    }
                } else if (line.startsWith("Place:")) {
                    if (lostItem != null) {
                        lostItem.setPlace(line.substring(6).trim());
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
        return Set.of("text", "txt");
    }
}