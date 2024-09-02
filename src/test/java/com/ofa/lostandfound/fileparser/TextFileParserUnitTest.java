package com.ofa.lostandfound.fileparser;

import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.exception.DocumentInvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextFileParserUnitTest {

    private TextFileParser textFileParser;

    @BeforeEach
    public void setUp() {
        textFileParser = new TextFileParser();
    }

    @Test
    public void testParseValidFile() throws Exception {
        // Given: a file with valid data
        String content = "ItemName: Phone\nQuantity: 1\nPlace: Library\nItemName: Wallet\nQuantity: 2\nPlace: Cafe";
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

        // When: parsing the file
        List<LostItem> result = textFileParser.parse(file);

        // Then: verify the result
        assertEquals(2, result.size(), "Should parse two lost items");
        assertEquals("Phone", result.get(0).getName(), "First item name should be 'Phone'");
        assertEquals(1, result.get(0).getQuantity(), "First item quantity should be 1");
        assertEquals("Library", result.get(0).getPlace(), "First item place should be 'Library'");
        assertEquals("Wallet", result.get(1).getName(), "Second item name should be 'Wallet'");
        assertEquals(2, result.get(1).getQuantity(), "Second item quantity should be 2");
        assertEquals("Cafe", result.get(1).getPlace(), "Second item place should be 'Cafe'");
    }


    @Test
    public void testParseFileWithMissingData() throws Exception {
        // Given: a file with missing data
        String content = "ItemName: \nQuantity: 0\nPlace: Library";
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());
        // When: parsing the file
        DocumentInvalidDataException exception = assertThrows(DocumentInvalidDataException.class, () -> textFileParser.parse(file));

        // Then: verify the exception and message
        assertEquals("Invalid data format", exception.getMessage());

    }

    @Test
    public void testAcceptedFileTypes() {
        // Given: the expected file types
        Set<String> expectedFileTypes = Set.of("text", "txt");

        // When: getting accepted file types
        Set<String> actualFileTypes = textFileParser.acceptedFileTypes();

        // Then: verify the accepted file types
        assertEquals(expectedFileTypes, actualFileTypes, "Accepted file types should match");
    }

    @Test
    public void testParseFileWithError() throws IOException {
        // Given: a file that causes an exception
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getInputStream()).thenThrow(new RuntimeException("File read error"));

        // When & Then: parsing the file should throw an exception
        Exception exception = assertThrows(Exception.class, () -> textFileParser.parse(file));
        assertEquals("Error parsing file: File read error", exception.getMessage());
    }
}
