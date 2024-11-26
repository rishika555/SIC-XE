package com.assembler.sicxe.dto;

import com.assembler.sicxe.model.SymbolTable;
import java.util.List;
import java.util.Objects;

public class AssembleResponseDTO {
    private List<String> errors;
    private SymbolTable pass1SymbolTable;
    private SymbolTable pass2SymbolTable;
    private String machineCode;

    // Constructor
    public AssembleResponseDTO() {}

    public AssembleResponseDTO(List<String> errors, SymbolTable pass1SymbolTable, SymbolTable pass2SymbolTable, String machineCode) {
        this.errors = errors;
        this.pass1SymbolTable = pass1SymbolTable;
        this.pass2SymbolTable = pass2SymbolTable;
        this.machineCode = machineCode;
    }

    // Getters and Setters
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public SymbolTable getPass1SymbolTable() {
        return pass1SymbolTable;
    }

    public void setPass1SymbolTable(SymbolTable pass1SymbolTable) {
        this.pass1SymbolTable = pass1SymbolTable;
    }

    public SymbolTable getPass2SymbolTable() {
        return pass2SymbolTable;
    }

    public void setPass2SymbolTable(SymbolTable pass2SymbolTable) {
        this.pass2SymbolTable = pass2SymbolTable;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    // Optional: Override toString(), equals(), and hashCode for convenience
    @Override
    public String toString() {
        return "AssembleResponseDTO{" +
                "errors=" + errors +
                ", pass1SymbolTable=" + pass1SymbolTable +
                ", pass2SymbolTable=" + pass2SymbolTable +
                ", machineCode='" + machineCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssembleResponseDTO that = (AssembleResponseDTO) o;
        return errors.equals(that.errors) && 
               pass1SymbolTable.equals(that.pass1SymbolTable) && 
               pass2SymbolTable.equals(that.pass2SymbolTable) && 
               machineCode.equals(that.machineCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, pass1SymbolTable, pass2SymbolTable, machineCode);
    }
}
