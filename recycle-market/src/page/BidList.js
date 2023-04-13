import React from 'react'
import BidTable from '../components/bid/BidTable'

function BidList() {
    const data = [
        {
          id: 1,
          code: 'P001',
          name: '아이폰 13',
          bidPrice: 1000000,
        },
        {
          id: 2,
          code: 'P002',
          name: '갤럭시 S21',
          bidPrice: 800000,
        },
        {
          id: 3,
          code: 'P003',
          name: '맥북 프로',
          bidPrice: 1500000,
        },
      ];
      
  return (
    <BidTable data={data} />
  )
}

export default BidList