import { CategoryScale, Chart } from 'chart.js';
import React, { useEffect, useRef, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import { Data } from './Test';

Chart.register(CategoryScale);

const ThemeColors = () => {
  const rootStyle = getComputedStyle(document.body);
  return {
    themeColor1: rootStyle.getPropertyValue('--theme-color-1').trim(),
    themeColor2: rootStyle.getPropertyValue('--theme-color-2').trim(),
    themeColor3: rootStyle.getPropertyValue('--theme-color-3').trim(),
    themeColor4: rootStyle.getPropertyValue('--theme-color-4').trim(),
    themeColor5: rootStyle.getPropertyValue('--theme-color-5').trim(),
    themeColor6: rootStyle.getPropertyValue('--theme-color-6').trim(),
    themeColor1_10: rootStyle.getPropertyValue('--theme-color-1-10').trim(),
    themeColor2_10: rootStyle.getPropertyValue('--theme-color-2-10').trim(),
    themeColor3_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    themeColor4_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    themeColor5_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    themeColor6_10: rootStyle.getPropertyValue('--theme-color-3-10').trim(),
    primaryColor: rootStyle.getPropertyValue('--primary-color').trim(),
    foregroundColor: rootStyle.getPropertyValue('--foreground-color').trim(),
    separatorColor: rootStyle.getPropertyValue('--separator-color').trim(),
  };
};
const chartTooltip = {
  backgroundColor: ThemeColors().foregroundColor,
  titleFontColor: ThemeColors().primaryColor,
  borderColor: ThemeColors().separatorColor,
  borderWidth: 0.5,
  bodyFontColor: ThemeColors().primaryColor,
  bodySpacing: 10,
  xPadding: 15,
  yPadding: 15,
  cornerRadius: 0.15,
};

const barChartOptions = {
  legend: {
    position: 'bottom',
    labels: {
      padding: 30,
      usePointStyle: true,
      fontSize: 12,
    },
  },
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    yAxes: [
      {
        gridLines: {
          display: true,
          lineWidth: 1,
          color: 'rgba(0,0,0,0.1)',
          drawBorder: false,
        },
        ticks: {
          beginAtZero: true,
          stepSize: 100,
          min: 300,
          max: 800,
          padding: 20,
        },
      },
    ],
    xAxes: [
      {
        gridLines: {
          display: false,
        },
      },
    ],
  },
  tooltips: chartTooltip,
};
const colors = ThemeColors();
export const barChartData = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June'],
  datasets: [
    {
      label: 'Cakes',
      borderColor: colors.themeColor1,
      backgroundColor: colors.themeColor1_10,
      data: [456, 479, 324, 569, 702, 600],
      borderWidth: 2,
    },
    {
      label: 'Desserts',
      borderColor: colors.themeColor2,
      backgroundColor: colors.themeColor2_10,
      data: [364, 504, 605, 400, 345, 320],
      borderWidth: 2,
    },
  ],
};

type Props = {
  data: any;
  shadow: boolean;
};

const BarChart2 = ({ data = barChartData, shadow = false }: Props) => {
  const [chartData, setChartData] = useState({
    labels: Data.map(d => d.year),
    datasets: [
      {
        label: 'Users Gained ',
        data: Data.map(d => d.userGain),
        backgroundColor: ['rgba(75,192,192,1)', '#ecf0f1', '#50AF95', '#f3ba2f', '#2a71d0'],
        borderColor: 'black',
        borderWidth: 2,
      },
    ],
  });

  return (
    <div className="App">
      <p>Using Chart.js in React</p>
      {/* <Pie
        data={chartData}
        options={{
          plugins: {
            title: {
              display: true,
              text: 'Users Gained between 2016-2020',
            },
          },
        }}
      /> */}
      <Bar
        data={chartData}
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
      />
    </div>
  );
};

export default BarChart2;
