import React from "react";
import ProgressBar from "../components/my-transaction/ProgressBar";
import TransactionNav from "../components/my-transaction/TransactionNav";
import DealInfo from "../components/my-transaction/DealInfo";

function TransactionDetail() {
    const data = [
        {
          id: 1,
          image: "https://via.placeholder.com/150",
          code: "PROD001",
          category: "의류",
          name: "맨투맨",
          price: 20000,
          seller: "판매자A",
        },
        {
          id: 2,
          image: "https://via.placeholder.com/150",
          code: "PROD002",
          category: "가전",
          name: "냉장고",
          price: 500000,
          seller: "판매자B",
        },
        {
          id: 3,
          image: "https://via.placeholder.com/150",
          code: "PROD003",
          category: "식품",
          name: "과일바구니",
          price: 30000,
          seller: "판매자C",
        },
        {
          id: 4,
          image: "https://via.placeholder.com/150",
          code: "PROD004",
          category: "생활용품",
          name: "이불세트",
          price: 70000,
          seller: "판매자D",
        },
      ];
      
  return (
    <div>
      <TransactionNav />
      <ProgressBar status={"결제 대기"} />
      <DealInfo product={data[0]}/>
    </div>
  );
}

export default TransactionDetail;
