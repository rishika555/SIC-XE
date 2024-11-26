import '../styles/SymbolTable.css';
const SymbolTable = ({ symbols }) => (
    <div>
      <h5>Symbol Table</h5>
      <table className="table">
        <thead>
          <tr>
            <th>Symbol</th>
            <th>Address</th>
          </tr>
        </thead>
        <tbody>
          {Object.entries(symbols).map(([symbol, { address }]) => (
            <tr key={symbol}>
              <td>{symbol}</td>
              <td>{address}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
  
  export default SymbolTable;
  