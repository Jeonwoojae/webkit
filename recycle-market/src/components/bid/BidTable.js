import React from 'react';
import './BidTable.css';

const BidTable = ({ data }) => {
  return (
    <table className="bid-table">
      <thead>
        <tr>
          <th>상품코드</th>
          <th>상품명</th>
          <th>내 입찰가</th>
          <th>관리</th>
        </tr>
      </thead>
      <tbody>
        {data.map((item) => (
          <tr key={item.id}>
            <td>{item.code}</td>
            <td>{item.name}</td>
            <td>{item.bidPrice}원</td>
            <td>
              <button className="btn-edit">수정하기</button>
              <button className="btn-delete">삭제하기</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default BidTable;
