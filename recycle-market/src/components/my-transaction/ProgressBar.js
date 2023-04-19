import React from 'react';

const ProgressBar = ({ status }) => {
  const statuses = ['거래 취소', '결제 대기', '결제 완료', '배송 중', '거래 완료'];
  const progress = statuses.indexOf(status) * 20;

  return (
    <div style={{ maxWidth: '400px', margin: '0 auto' }}>
      <div style={{ width: `${progress+7}%`, height: '20px', backgroundColor: `rgba(50, 205, 50, ${progress / 100})`, transition: 'width 1s ease-in-out' }} />
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
