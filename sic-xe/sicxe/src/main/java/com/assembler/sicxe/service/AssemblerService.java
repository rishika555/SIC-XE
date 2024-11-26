package com.assembler.sicxe.service;

import com.assembler.sicxe.dto.AssembleResponseDTO;
import com.assembler.sicxe.model.SymbolTable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssemblerService {

    // Extended SIC/XE opcode table
    private static final Map<String, String> OPCODE_TABLE = Map.ofEntries(
        Map.entry("LDA", "00"),
        Map.entry("STA", "0C"),
        Map.entry("ADD", "18"),
        Map.entry("SUB", "1C"),
        Map.entry("MUL", "20"),
        Map.entry("DIV", "24"),
        Map.entry("COMP", "28"),
        Map.entry("J", "3C"),
        Map.entry("JEQ", "30"),
        Map.entry("JGT", "34"),
        Map.entry("JLT", "38"),
        Map.entry("JSUB", "48"),
        Map.entry("LDX", "04"),
        Map.entry("STX", "10"),
        Map.entry("RSUB", "4C"),
        Map.entry("CLEAR", "B4"), // Format 2 instruction
        Map.entry("TIXR", "B8")   // Format 2 instruction
    );

    public AssembleResponseDTO assembleCode(String sourceCode) {
        List<String> errors = new ArrayList<>();
        SymbolTable pass1SymbolTable = new SymbolTable();
        SymbolTable pass2SymbolTable = new SymbolTable();
        StringBuilder machineCode = new StringBuilder();

        if (sourceCode == null || sourceCode.trim().isEmpty()) {
            errors.add("Source code cannot be empty");
            return new AssembleResponseDTO(errors, pass1SymbolTable, pass2SymbolTable, machineCode.toString());
        }

        // Split the source code into lines
        String[] lines = sourceCode.split("\n");
        int locationCounter = 0;
        boolean startFound = false;

        // Pass 1: Symbol table generation (Label Addresses)
        for (String line : lines) {
            line = line.trim().replaceAll("\\s+", " "); // Normalize spaces and trim

            if (line.isEmpty() || line.startsWith(";")) continue; // Skip empty lines and comments

            String[] tokens = line.split(" "); // Split by space
            if (tokens.length == 0) continue;

            // Handle the START directive
            if (tokens[0].equalsIgnoreCase("START")) {
                startFound = true;
                if (tokens.length > 1 && !tokens[1].isEmpty()) {
                    try {
                        locationCounter = Integer.parseInt(tokens[1], 16);
                    } catch (NumberFormatException e) {
                        errors.add("Invalid START address: " + tokens[1]);
                        locationCounter = 0;
                    }
                } else {
                    errors.add("Missing address after START directive");
                    locationCounter = 0;
                }
                continue;
            }

            // Handle Labels and Opcodes
            String label = "";
            String opcode = "";
            String operand = "";

            if (tokens.length > 1 && !OPCODE_TABLE.containsKey(tokens[0])) {
                label = tokens[0];
                opcode = tokens[1];
                operand = tokens.length > 2 ? tokens[2] : "";
            } else {
                opcode = tokens[0];
                operand = tokens.length > 1 ? tokens[1] : "";
            }

            // Add label to Pass 1 symbol table
            if (!label.isEmpty()) {
                if (pass1SymbolTable.containsSymbol(label)) {
                    errors.add("Duplicate label: " + label);
                } else {
                    pass1SymbolTable.addEntry(label, locationCounter);
                }
            }

            // Handle Opcodes and increment location counter
            if (opcode.startsWith("+")) { // Format 4 (4 bytes)
                locationCounter += 4;
            } else if (OPCODE_TABLE.containsKey(opcode)) {
                if (opcode.equals("CLEAR") || opcode.equals("TIXR")) {
                    locationCounter += 2; // Format 2
                } else {
                    locationCounter += 3; // Format 3
                }
            } else if (opcode.equalsIgnoreCase("WORD")) {
                locationCounter += 3;
            } else if (opcode.equalsIgnoreCase("RESW")) {
                try {
                    locationCounter += 3 * Integer.parseInt(operand);
                } catch (NumberFormatException e) {
                    errors.add("Invalid RESW operand: " + operand);
                }
            } else if (opcode.equalsIgnoreCase("RESB")) {
                try {
                    locationCounter += Integer.parseInt(operand);
                } catch (NumberFormatException e) {
                    errors.add("Invalid RESB operand: " + operand);
                }
            } else if (opcode.equalsIgnoreCase("BYTE")) {
                if (operand.startsWith("C'") && operand.endsWith("'")) {
                    locationCounter += operand.length() - 3;
                } else if (operand.startsWith("X'") && operand.endsWith("'")) {
                    locationCounter += (operand.length() - 3) / 2;
                } else {
                    errors.add("Invalid BYTE operand: " + operand);
                }
            }
        }

        // Reset location counter for Pass 2
        locationCounter = startFound ? Integer.parseInt(lines[0].split("\\s+")[1], 16) : 0;

        // Pass 2: Generate Symbol Table and Machine Code
        for (String line : lines) {
            line = line.trim().replaceAll("\\s+", " "); // Normalize spaces and trim
            if (line.isEmpty() || line.startsWith(";")) continue; // Skip empty lines and comments

            String[] tokens = line.split(" "); // Split by space
            if (tokens.length == 0) continue;

            String label = "";
            String opcode = "";
            String operand = "";

            if (tokens.length > 1 && !OPCODE_TABLE.containsKey(tokens[0])) {
                label = tokens[0];
                opcode = tokens[1];
                operand = tokens.length > 2 ? tokens[2] : "";
            } else {
                opcode = tokens[0];
                operand = tokens.length > 1 ? tokens[1] : "";
            }

            // Add label to Pass 2 symbol table
            if (!label.isEmpty() && pass1SymbolTable.containsSymbol(label)) {
                pass2SymbolTable.addEntry(label, locationCounter);
            }

            // Generate machine code for each instruction
            if (opcode.equalsIgnoreCase("RSUB")) {
                machineCode.append("4C0000\n");
            } else if (OPCODE_TABLE.containsKey(opcode)) {
                String opCodeHex = OPCODE_TABLE.get(opcode);
                Integer address = pass1SymbolTable.getAddress(operand);

                if (opcode.equals("CLEAR") || opcode.equals("TIXR")) {
                    machineCode.append(opCodeHex).append("0").append("\n");
                } else {
                    if (address != null) {
                        machineCode.append(opCodeHex).append(String.format("%04X", address)).append("\n");
                    } else {
                        errors.add("Undefined symbol: " + operand);
                        machineCode.append(opCodeHex).append("0000\n");
                    }
                }
            }

            // Increment location counter for each instruction
            if (opcode.startsWith("+")) {
                locationCounter += 4;
            } else if (OPCODE_TABLE.containsKey(opcode)) {
                if (opcode.equals("CLEAR") || opcode.equals("TIXR")) {
                    locationCounter += 2;
                } else {
                    locationCounter += 3;
                }
            }
        }

        return new AssembleResponseDTO(errors, pass1SymbolTable, pass2SymbolTable, machineCode.toString());
    }
}
