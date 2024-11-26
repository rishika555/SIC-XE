package com.assembler.sicxe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class SymbolEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "address", nullable = false)
    private Integer address;

    // Many SymbolEntry instances can belong to one SymbolTable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol_table_id")
    @JsonIgnore // Prevents infinite recursion during serialization
    private SymbolTable symbolTable;

    // Default constructor
    public SymbolEntry() {}

    // Constructor to create SymbolEntry directly from symbol, address, and symbolTable
    public SymbolEntry(String symbol, Integer address, SymbolTable symbolTable) {
        this.symbol = symbol;
        this.address = address;
        this.symbolTable = symbolTable;
    }

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for 'symbol'
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    // Getter and Setter for 'address'
    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    // Getter and Setter for 'symbolTable'
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    // Custom toString method for symbol entries
    @Override
    public String toString() {
        return "SymbolEntry [symbol=" + symbol + ", address=" + address + "]";
    }
}
