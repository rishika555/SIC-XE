package com.assembler.sicxe.dto;

public class AssembleRequestDTO {
    private String sourceCode;
    private String assemblerOptions;

    // Getters and Setters
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getAssemblerOptions() {
        return assemblerOptions;
    }

    public void setAssemblerOptions(String assemblerOptions) {
        this.assemblerOptions = assemblerOptions;
    }
}
