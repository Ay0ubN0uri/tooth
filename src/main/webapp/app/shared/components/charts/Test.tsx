import React from 'react';
import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';
import { useState } from 'react';
import { Bar, Pie } from 'react-chartjs-2';

Chart.register(CategoryScale);

export const Data = [
  {
    id: 1,
    year: 2016,
    userGain: 80000,
    userLost: 823,
  },
  {
    id: 2,
    year: 2017,
    userGain: 45677,
    userLost: 345,
  },
  {
    id: 3,
    year: 2018,
    userGain: 78888,
    userLost: 555,
  },
  {
    id: 4,
    year: 2019,
    userGain: 90000,
    userLost: 4555,
  },
  {
    id: 5,
    year: 2020,
    userGain: 4300,
    userLost: 234,
  },
];

const colors = {
  gray: {
    100: '#f6f9fc',
    200: '#e9ecef',
    300: '#dee2e6',
    400: '#ced4da',
    500: '#adb5bd',
    600: '#8898aa',
    700: '#525f7f',
    800: '#32325d',
    900: '#212529',
  },
  theme: {
    default: '#172b4d',
    primary: '#5e72e4',
    secondary: '#f4f5f7',
    info: '#11cdef',
    success: '#2dce89',
    danger: '#f5365c',
    warning: '#fb6340',
  },
  black: '#12263F',
  white: '#FFFFFF',
  transparent: 'transparent',
};

const Test = () => {
  const [chartData, setChartData] = useState({
    labels: Data.map(data => data.year),
    datasets: [
      {
        label: 'Users Gained ',
        data: Data.map(data => data.userGain),
        backgroundColor: ['rgba(75,192,192,1)', '#ecf0f1', '#50AF95', '#f3ba2f', '#2a71d0'],
        borderColor: 'black',
        borderWidth: 2,
      },
    ],
  });

  return (
    <div className="card">
      <div className="card-header">
        <h6 className="surtitle">Overview</h6>
        <h5 className="h3 mb-0">Number of students per groups</h5>
      </div>
      <div className="card-body">
        <div className="chart">
          <Bar
            className="chart-canvas"
            options={{
              plugins: {
                title: {
                  display: true,
                  text: 'Users Gained between 2016-2020',
                },
                legend: {
                  display: false,
                },
              },
            }}
            // data={{
            //   labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            //   datasets: [
            //     {
            //       label: 'Performance',
            //       data: [0, 20, 10, 30, 15, 40, 20, 60, 60],
            //     },
            //   ],
            // }}
            data={chartData}
            // options={{
            //   plugins: {
            //     title: {
            //       display: true,
            //       text: 'Users Gained between 2016-2020',
            //     },
            //     legend: {
            //       display: false,
            //     },
            //   },
            // }}
          />
        </div>
      </div>
    </div>
  );
};

export default Test;
