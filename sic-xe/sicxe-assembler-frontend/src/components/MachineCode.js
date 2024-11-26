import '../styles/machineCode.css';

const MachineCode = ({ machineCode }) => (
  <div className="machine-code">
    <h5>Machine Code</h5>
    <pre>{machineCode}</pre>
  </div>
);

export default MachineCode;
