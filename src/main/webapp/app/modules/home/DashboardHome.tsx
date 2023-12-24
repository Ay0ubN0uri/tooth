import { useAppDispatch, useAppSelector } from 'app/config/store';
import AdminCards from 'app/shared/components/cards/AdminCards';
import BarChart, { generateRandomColors } from 'app/shared/components/charts/BarChart';
import { Data } from 'app/shared/components/charts/Test';
import React, { useEffect, useState } from 'react';
import { fetchStatsData, getCountStats, getStudentsPerGroup } from './home.reducer';
import LoadingSpinner from 'app/shared/components/LoadingSpinner';

const DashboardHome = () => {
  const dispatch = useAppDispatch();
  const loading = useAppSelector(state => state.dashboard.loading);
  const countStats = useAppSelector(state => state.dashboard.countStats);
  const studentsPerGroup = useAppSelector(state => state.dashboard.studentsPerGroup);
  const pwsPerGroup = useAppSelector(state => state.dashboard.pwsPerGroup);

  const [studentsPerGroupChart, setStudentsPerGroupChart] = useState({});
  const [pwsPerGroupChart, setPwsPerGroupChart] = useState({});
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    dispatch(fetchStatsData());
    return () => {};
  }, [dispatch]);

  useEffect(() => {
    if (!loading) {
      const names = studentsPerGroup.map(item => item.name);
      const counts = studentsPerGroup.map(item => item.count);
      const pwsNames = pwsPerGroup.map(item => item.name);
      const pwsCounts = pwsPerGroup.map(item => item.count);
      setStudentsPerGroupChart({
        labels: names,
        datasets: [
          {
            label: 'Students',
            data: counts,
            backgroundColor: generateRandomColors(counts.length),
            borderColor: 'black',
            borderWidth: 1,
          },
        ],
      });
      setPwsPerGroupChart({
        labels: pwsNames,
        datasets: [
          {
            label: 'Practice work',
            data: pwsCounts,
            backgroundColor: generateRandomColors(pwsCounts.length),
            borderColor: 'black',
            borderWidth: 1,
          },
        ],
      });
      setIsLoading(false);
    }
    return () => {};
  }, [loading]);

  return (
    <>
      {isLoading ? (
        <LoadingSpinner />
      ) : (
        <>
          <div className="header-body mt-3">
            <AdminCards {...countStats} />
          </div>
          <div className="row">
            <div className="col-xl-6">
              <BarChart title="Number of students per groups" subtitle="Overview" chartData={studentsPerGroupChart} />
            </div>
            <div className="col-xl-6">
              <BarChart title="Number of students per groups" subtitle="Overview" chartData={pwsPerGroupChart} />
            </div>
          </div>
        </>
      )}
    </>
  );
};

export default DashboardHome;
