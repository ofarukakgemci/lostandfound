package com.ofa.lostandfound.service;

import com.ofa.lostandfound.fileparser.FileParser;
import com.ofa.lostandfound.fileparser.PdfFileParser;
import com.ofa.lostandfound.fileparser.TextFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentProcessingServiceUnitTest {

    @Mock
    private LostItemServiceImpl lostItemService;

    @Mock
    private TextFileParser textFileParser;
    @Mock
    private PdfFileParser pdfFileParser;

    private DocumentProcessingService documentProcessingService;
    @BeforeEach
    public void setup() {
        documentProcessingService = new DocumentProcessingServiceImpl(lostItemService, List.of(textFileParser, pdfFileParser));
    }
    @Test
    public void testParseFile() throws Exception {
        // given
        MultipartFile file = mock(MultipartFile.class);

        when(textFileParser.accept("csv")).thenReturn(true);
        when(pdfFileParser.accept("pdf")).thenReturn(true);

        // when
        documentProcessingService.process(file, "csv");
        documentProcessingService.process(file, "pdf");

        // then
        verify(lostItemService, times(2)).saveAll(any());
    }

    @Test
    public void testParseFileUnsupportedFileType() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        String fileType = "xls";
        when(textFileParser.accept(fileType)).thenReturn(false);
        when(pdfFileParser.accept(fileType)).thenReturn(false);

        // when then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> documentProcessingService.process(file, fileType));
        assertEquals("Unsupported File Type", exception.getMessage());
    }

    @Test
    public void testPickFileParser() {
        // Arrange
        List<FileParser> fileParserList = Arrays.asList(textFileParser, mock(FileParser.class));
        when(textFileParser.accept("csv")).thenReturn(true);

        // Act
        FileParser pickedFileParser = documentProcessingService.pickFileParser("csv");

        // Assert
        assertEquals(textFileParser, pickedFileParser);
    }
}