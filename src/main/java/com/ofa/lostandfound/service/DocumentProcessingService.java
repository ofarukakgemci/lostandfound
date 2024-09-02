package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.exception.DocumentNotProcessedException;
import com.ofa.lostandfound.fileparser.FileParser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface for DocumentProcessingService.
 * <p>
 * This interface defines the contract for the DocumentProcessingService class.
 */
public interface DocumentProcessingService {

    /**
     * Parses a file and saves the lost items to the database.
     *
     * @param file     The file to be parsed.
     * @param fileType The type of the file.
     * @return A list of LostItemDTO objects.
     * @throws DocumentNotProcessedException If an error occurs during file parsing or saving.
     */
    List<LostItemDTO> process(MultipartFile file, String fileType) throws DocumentNotProcessedException;

    /**
     * Picks a file parser based on the file type.
     *
     * @param fileType The type of the file.
     * @return A FileParser object.
     */
    FileParser pickFileParser(String fileType);
}