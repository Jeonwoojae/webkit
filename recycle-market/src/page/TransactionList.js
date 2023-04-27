import React, { useEffect, useState } from "react";
import TransactionTable from "../components/transaction/TransactionTable";
import call from "../service/ApiService";

function TransactionList() {
  const [transactionList, setTransactionList] = useState([]);

  useEffect(() => {
    call(`/api/v1/transactions/users`, "GET", null)
      .then((data) => {
        console.log(data);
        setTransactionList(data); // 받아온 데이터를 상태값으로 설정
      })
      .catch((error) => {
        console.error("거래 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
  }, []); // 컴포넌트가 처음 렌더링될 때만 실행

  return <TransactionTable transactions={transactionList} />;
}

export default TransactionList;
