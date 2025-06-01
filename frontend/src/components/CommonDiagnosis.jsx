import { useEffect } from 'react';
import { getMostCommonDiagnosis } from '../api/reportApi';

import './CommonDiagnosis.css';

const CommonDiagnosis = () => {
  const [commonDiagnoses, setCommonDiagnoses] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchMostCommonDiagnoses = async () => {
      setIsLoading(true);
      try {
        const data = await getMostCommonDiagnosis();
        setCommonDiagnoses(data);
      } catch (error) {
        console.error('Error fetching common diagnoses:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchMostCommonDiagnoses();
  }, []);

  if (isLoading) return <p>Loading diagnoses...</p>;

  return (
    <div className="report-content">
      <h2 className="section-title">Most Common Diagnoses</h2>
      <div className="diagnoses-list">
        {commonDiagnoses.map((diagnosis, index) => (
          <div key={diagnosis.code} className="diagnosis-card">
            <div className="diagnosis-rank">#{index + 1}</div>
            <div className="diagnosis-info">
              <h3 className="diagnosis-name">{diagnosis.name}</h3>
              <p className="diagnosis-code">{diagnosis.code}</p>
            </div>
            <div className="diagnosis-count">{diagnosis.count} cases</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CommonDiagnosis;
