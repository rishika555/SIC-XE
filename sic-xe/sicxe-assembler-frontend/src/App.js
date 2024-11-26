import React, { useState } from 'react';
import axios from 'axios';
import CodeEditor from './components/CodeEditor';
import ErrorList from './components/ErrorList';
import MachineCode from './components/MachineCode';
import SymbolTable from './components/SymbolTable';
import './App.css';

const App = () => {
  const [code, setCode] = useState('');
  const [errors, setErrors] = useState([]);
  const [symbolTable, setSymbolTable] = useState({});
  const [machineCode, setMachineCode] = useState('');

  // Handle submit button click to assemble the code
  const handleSubmit = async () => {
    try {
      const response = await axios.post('http://localhost:8080/assembler/assemble', {
        sourceCode: code,
        assemblerOptions: '',
      });

      const result = response.data;

      // Check for errors
      if (result.errors && result.errors.length > 0) {
        setErrors(result.errors);
      } else {
        setErrors([]);
        setSymbolTable(result.pass1SymbolTable.symbols || {});
        setMachineCode(result.machineCode || '');
      }
    } catch (error) {
      console.error('Error during assembly:', error);
      setErrors(['An error occurred while assembling the code.']);
    }
  };

  return (
    <div className="container mt-4">
      <h1>SIC/XE Assembler</h1>
      <div>
        <CodeEditor code={code} onCodeChange={setCode} />
        {/* Updated button styling */}
        <button className="btn btn-primary" onClick={handleSubmit}>
          Assemble Code
        </button>
      </div>

      <ErrorList errors={errors} />
      <SymbolTable symbols={symbolTable} />
      <MachineCode machineCode={machineCode} />
    </div>
  );
};

export default App;
