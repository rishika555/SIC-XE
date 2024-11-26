import axios from 'axios';

const assembleCode = async (sourceCode) => {
  try {
    const response = await axios.post('http://localhost:8080/assemble', { sourceCode });
    return response.data;
  } catch (error) {
    console.error('Error assembling code:', error);
    return { errors: ['Failed to assemble code'] };
  }
};

export default { assembleCode };
