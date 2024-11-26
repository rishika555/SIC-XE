import '../styles/Error.css';
const ErrorList = ({ errors }) => (
    <div className="alert alert-danger">
      {errors.length === 0 ? <p>No errors</p> : errors.map((error, index) => <p key={index}>{error}</p>)}
    </div>
  );
  
  export default ErrorList;