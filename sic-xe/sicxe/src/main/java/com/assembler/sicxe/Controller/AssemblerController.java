package com.assembler.sicxe.Controller;

import com.assembler.sicxe.dto.AssembleRequestDTO;
import com.assembler.sicxe.dto.AssembleResponseDTO;
import com.assembler.sicxe.service.AssemblerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/assembler")
public class AssemblerController {

    private static final Logger logger = LoggerFactory.getLogger(AssemblerController.class);

    @Autowired
    private AssemblerService assemblerService;

    // Endpoint to assemble code
    @PostMapping("/assemble")
    public ResponseEntity<AssembleResponseDTO> assemble(@RequestBody AssembleRequestDTO request) {
        logger.info("Received Request: Source Code: {}, Assembler Options: {}", request.getSourceCode(), request.getAssemblerOptions());

        // Validate the incoming request
        if (request == null || request.getSourceCode() == null || request.getSourceCode().trim().isEmpty()) {
            logger.error("Source code cannot be empty");
            return new ResponseEntity<>(new AssembleResponseDTO(
                    java.util.Collections.singletonList("Source code cannot be empty"), null, null, null),
                    HttpStatus.BAD_REQUEST);
        }

        if (request.getAssemblerOptions() == null || request.getAssemblerOptions().trim().isEmpty()) {
            logger.warn("Assembler options are not provided");
        }

        // Assemble the source code using the service
        AssembleResponseDTO response = assemblerService.assembleCode(request.getSourceCode());

        // Check for errors in the assembly process
        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            logger.error("Assembly Errors: {}", response.getErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Successful response
        logger.info("Assembly Successful!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
