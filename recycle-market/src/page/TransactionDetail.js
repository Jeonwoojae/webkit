import React, { useEffect, useState } from "react";
import ProgressBar from "../components/my-transaction/ProgressBar";
import DealInfo from "../components/my-transaction/DealInfo";
import { useParams } from "react-router-dom";
import call from "../service/ApiService";

function TransactionDetail() {
  const { transactionId } = useParams();
  const [transaction, setTransaction] = useState({});
  const [updated, setUpdated] = useState(false);

  useEffect(() => {
    call(`/api/v1/transactions/${transactionId}`, "GET", null)
      .then((data) => {
        console.log(data);
        setTransaction(data); // 받아온 데이터를 상태값으로 설정
        setUpdated(false);
      })
      .catch((error) => {
        console.error("거래 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
  }, [updated]); // 컴포넌트가 처음 렌더링될 때만 실행

  const paymentRequest = (method) =>{
    call(`/api/v1/transactions/${transactionId}/pay?method=${method}`, "PATCH", null)
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error("결제 중 오류가 발생했습니다.", error);
      });
  }

  const addTrackingCodeRequest = (code) =>{
    const obj = {trackingNumber: code};
    call(`/api/v1/transactions/${transactionId}/ship`, "PATCH", obj)
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error("운송장 등록 중 오류가 발생했습니다.", error);
      });
  }

  const completeTransactionRequest = () => {
    call(`/api/v1/transactions/${transactionId}/complete`, "PATCH", null)
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error("수취 확인 중 오류가 발생했습니다.", error);
      });
  }

  const cancleTransactionRequest = () => {
    call(`/api/v1/transactions/${transactionId}/cancle`, "PATCH", null)
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error("거래 취소 중 오류가 발생했습니다.", error);
      });
  }

  return (
    <div style={{marginTop: "40px"}}>
      <ProgressBar status={transaction.transactionState} />
      <DealInfo transaction={transaction} setUpdated={setUpdated}/>
    </div>
  );
}

export default TransactionDetail;
