package com.ofa.lostandfound.service;

import com.ofa.lostandfound.dto.LostItemDTO;
import com.ofa.lostandfound.entity.LostItem;
import com.ofa.lostandfound.exception.DocumentNotProcessedException;
import com.ofa.lostandfound.fileparser.FileParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentProcessingServiceImpl implements DocumentProcessingService {

    private final LostItemServiceImpl lostItemService;
    private final List<FileParser> fileParserList;

    public DocumentProcessingServiceImpl(LostItemServiceImpl lostItemService, List<FileParser> fileParserList) {
        this.lostItemService = lostItemService;
        this.fileParserList = fileParserList;
    }

    public List<LostItemDTO> process(MultipartFile file, String fileType) throws DocumentNotProcessedException {
        List<LostItem> lostItems = pickFileParser(fileType).parse(file);
        return lostItemService.saveAll(lostItems);
    }

    public FileParser pickFileParser(String fileType) {
        return fileParserList.stream().filter(fp -> fp.accept(fileType))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Unsupported File Type"));
    }
}