import React from 'react';
import { ThreeCircles } from 'react-loader-spinner';

const LoadingSpinner = () => {
  return (
    <div className="d-flex align-items-center justify-content-center">
      <ThreeCircles
        visible={true}
        height="100"
        width="100"
        color="#3498db"
        ariaLabel="three-circles-loading"
        wrapperStyle={{}}
        wrapperClass=""
      />
    </div>
  );
};

export default LoadingSpinner;
