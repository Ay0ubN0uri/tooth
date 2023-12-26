import React, { useEffect, useState } from 'react';
import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';
import { Bar, Line } from 'react-chartjs-2';
import { Input, Label } from 'reactstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/student/student.reducer';
import axios from 'axios';

Chart.register(CategoryScale);

interface ChartCardProps {
  subtitle: string;
  title: string;
}

const LineChart = ({ subtitle, title }: ChartCardProps) => {
  const dispatch = useAppDispatch();
  const studentList = useAppSelector(state => state.student.entities);
  const loading = useAppSelector(state => state.student.loading);
  const [student, setStudent] = useState(null);
  const [chartData, setChartData] = useState<any>(null);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: ``,
      }),
    );
  };

  useEffect(() => {
    getAllEntities();
  }, []);

  useEffect(() => {
    if (!loading) {
      if (studentList.length > 0) {
        setStudent(studentList[0].id);
      }
    }
    return () => {};
  }, [loading]);

  const handleChange = async event => {
    console.log(event.target.value);
    const resp = await axios.get<any>(`/api/stats/notes/${event.target.value}`);
    console.log(resp.data);
    setChartData(resp.data);
  };

  return (
    <div className="card">
      <div className="card-header">
        <h6 className="surtitle">{subtitle}</h6>
        <h5 className="h3 mb-0">{title}</h5>
      </div>
      <div className="card-body">
        <div className="chart">
          <div className="mb-3">
            <Input
              id="exampleSelect"
              name="select"
              type="select"
              // value={student}
              onChange={handleChange}
            >
              {studentList &&
                studentList.map((std, i) => (
                  <option value={std.id} key={std.id}>
                    {`${std.user.firstName} ${std.user.lastName}`}
                  </option>
                ))}
            </Input>
          </div>
          {chartData && (
            <Line
              className="chart-canvas"
              data={chartData}
              options={{
                // responsive: true,
                plugins: {
                  // legend: {
                  //     position: 'top' as const,
                  // },
                },
                // plugins: {
                //     // title: {
                //     //   display: true,
                //     //   text: title,
                //     // },
                //     legend: {
                //         display: false,
                //     },
                // },
              }}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default LineChart;
