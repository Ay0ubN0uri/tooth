import React from 'react';
import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';
import { Bar } from 'react-chartjs-2';

Chart.register(CategoryScale);

interface ChartCardProps {
  subtitle: string;
  title: string;
  chartData: any;
}

const getRandomColor = () => {
  const letters = '0123456789ABCDEF'.split('');
  let color = '#';
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
};
export const generateRandomColors = length => {
  return Array.from({ length }, () => getRandomColor());
};

const BarChart = ({ subtitle, title, chartData }: ChartCardProps) => {
  return (
    <div className="card">
      <div className="card-header">
        <h6 className="surtitle">{subtitle}</h6>
        <h5 className="h3 mb-0">{title}</h5>
      </div>
      <div className="card-body">
        <div className="chart">
          <Bar
            className="chart-canvas"
            data={chartData}
            options={{
              plugins: {
                // title: {
                //   display: true,
                //   text: title,
                // },
                legend: {
                  display: false,
                },
              },
            }}
          />
        </div>
      </div>
    </div>
  );
};

export default BarChart;
