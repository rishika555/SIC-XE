import React from 'react';
import '../styles/codeEditor.css';

const CodeEditor = ({ code, onCodeChange }) => {
  return (
    <div className="form-group">
      {/* Heading */}
      <label htmlFor="codeEditor">Enter SIC/XE Assembly Code</label>
      
      {/* Text Editor */}
      <textarea
        id="codeEditor"
        className="form-control"
        rows="10"
        value={code}
        onChange={(e) => onCodeChange(e.target.value)}
      />
    </div>
  );
};

export default CodeEditor;
