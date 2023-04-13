import React from "react";
import TransactionTable from "../components/transaction/TransactionTable";

function TransactionList() {
  const data = [
    {
      code: "dede01",
      productCode: "A001",
      winner: "John",
      price: 200000,
      seller: "Amy",
      status: "배송중",
    },
    {
      code: "dede02",
      productCode: "B002",
      winner: "David",
      price: 150000,
      seller: "Chris",
      status: "거래완료",
    },
    {
      code: "dede03",
      productCode: "C003",
      winner: "Emma",
      price: 180000,
      seller: "Frank",
      status: "결제대기",
    },
    {
      code: "dede04",
      productCode: "D004",
      winner: "Gina",
      price: 220000,
      seller: "Henry",
      status: "거래취소",
    },
  ];

  return <TransactionTable products={data} />;
}

export default TransactionList;
