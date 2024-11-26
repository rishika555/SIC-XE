package com.assembler.sicxe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@Entity
public class SymbolTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Using a HashMap to store symbols and their corresponding entries
    @OneToMany(mappedBy = "symbolTable", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "symbol")
    private Map<String, SymbolEntry> symbols = new HashMap<>();

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter for symbols
    public Map<String, SymbolEntry> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, SymbolEntry> symbols) {
        this.symbols = symbols;
    }

    // Method to add a symbol entry to the table
    public void addEntry(String symbol, Integer address) {
        SymbolEntry entry = symbols.get(symbol);
        if (entry == null) {
            // Add new entry if not present
            entry = new SymbolEntry(symbol, address, this);
            symbols.put(symbol, entry);
        } else {
            // Update the address if it was previously added
            entry.setAddress(address);
        }
    }

    // Method to get the address of a symbol
    public Integer getAddress(String symbol) {
        SymbolEntry entry = symbols.get(symbol);
        return entry != null ? entry.getAddress() : null;
    }

    // Method to check if a symbol exists in the table
    public boolean containsSymbol(String symbol) {
        return symbols.containsKey(symbol);
    }

    // Custom toString method to print all symbol entries
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, SymbolEntry> entry : symbols.entrySet()) {
            sb.append("Symbol: ").append(entry.getKey())
              .append(", Address: ").append(entry.getValue().getAddress()).append("\n");
        }
        return sb.toString();
    }
}
