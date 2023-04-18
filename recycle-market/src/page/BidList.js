import React, { useEffect, useState } from 'react'
import BidTable from '../components/bid/BidTable'
import call from '../service/ApiService';

function BidList() {
  const [bidList,setBidList]=useState([]);

  useEffect(() => {
    call(`/api/v1/bids`, "GET", null)
      .then((data) => {
        console.log(data);
        setBidList(data); // 받아온 데이터를 상태값으로 설정
      })
      .catch((error) => {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
  }, []); // 컴포넌트가 처음 렌더링될 때만 실행

  return (
    <BidTable data={bidList} setData={setBidList} />
  )
}

export default BidList