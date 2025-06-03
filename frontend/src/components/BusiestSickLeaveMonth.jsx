import { useState, useEffect } from 'react';
import { getBusiestSickLeaveMonth } from '../api/reportApi';
import './BusiestSickLeaveMonth.css';

const BusiestSickLeaveMonth = () => {
  const [data, setData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await getBusiestSickLeaveMonth();
        setData(result);
      } catch (err) {
        console.error('Error fetching sick leave data:', err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  const getMonthName = (monthNumber) => {
    const months = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ];
    return months[monthNumber - 1] || '';
  };

  return (
    <div className="busiest-leave-container">
      <div className="busiest-leave-header">
        <h2 className="busiest-leave-title">Busiest Sick Leave Month</h2>
        <p>The month with the highest number of sick leaves issued</p>
      </div>

      {isLoading && (
        <div className="loading-state">Loading sick leave statistics...</div>
      )}

      {data && !isLoading && (
        <div className="stats-card">
          <div className="month-display">{getMonthName(data.month)}</div>
          <div className="year-display">{data.year}</div>

          <div className="total-leaves">{data.totalSickLeaves}</div>
          <div className="total-label">Sick Leaves Issued</div>

          <div className="context-stats">
            <p>
              This was the peak month for sick leave issuance across all doctors
              and patients.
            </p>
            <p>
              On average, {Math.round(data.totalSickLeaves / 30)} sick leaves
              were issued per day during this month.
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default BusiestSickLeaveMonth;
