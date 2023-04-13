import React from 'react';

const ProgressBar = ({ status }) => {
  const statuses = ['결제 대기', '결제 완료', '배송중', '거래확정', '거래취소'];
  const progress = statuses.indexOf(status) * 20;

  return (
    <div style={{ maxWidth: '400px', margin: '0 auto' }}>
      <div style={{ width: `${progress}%`, height: '20px', backgroundColor: 'green' }} />
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        {statuses.map((statusName, index) => (
          <div key={index} style={{ flex: 1, textAlign: 'center' }}>
            {status === statusName && <strong>{statusName}</strong>}
            {status !== statusName && statusName}
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProgressBar;
