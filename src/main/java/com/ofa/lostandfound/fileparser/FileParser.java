package com.ofa.lostandfound.fileparser;

import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.exception.DocumentInvalidDataException;
import com.ofa.lostandfound.exception.DocumentNotProcessedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface FileParser {
    List<LostItem> parse(MultipartFile file) throws DocumentNotProcessedException;


    Set<String> acceptedFileTypes();


    default boolean accept(String fileType) {
        return acceptedFileTypes().contains(fileType.toLowerCase());
    }

    // require further enhancement with the repetitive logic in both textparser and pdfparser
    default void checkValid(LostItem lostItem) throws DocumentInvalidDataException {
        boolean isValidLostItem = lostItem != null && lostItem.getName() != null && !lostItem.getName().trim().isEmpty()
                && lostItem.getPlace() != null && !lostItem.getPlace().trim().isEmpty()
                && lostItem.getQuantity() > 0;
        if (!isValidLostItem) throw new DocumentInvalidDataException();

    }
}
