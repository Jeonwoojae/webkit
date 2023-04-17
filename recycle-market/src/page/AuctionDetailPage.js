import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import call from "../service/ApiService";
import parsingDate from "../service/ParsingDate";
import parsingPrice from "../service/ParsingPrice";

const AuctionDetailPage = () => {
  const { id } = useParams();
  const [productInfo, setProductInfo] = useState({});

  useEffect(() => {
    call(`/api/v1/products/${id}`, "GET", null)
      .then((data) => {
        console.log(data);
        setProductInfo(data); // 받아온 데이터를 상태값으로 설정
      })
      .catch((error) => {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
  }, []); // 컴포넌트가 처음 렌더링될 때만 실행

  // 예시 데이터
  const productData = {
    code: "P00001",
    category: "의류",
    name: "니트",
    currentBid: 50000,
    endTime: "2023-04-30 23:59:59",
    seller: "seller1",
    imageSrc: "https://dummyimage.com/400x400/000/fff",
    auctionStatus: "in progress",
    description: "this is my product"
  };

  return (
    <div
      style={{ display: "flex", justifyContent: "center", marginTop: "50px" }}
    >
      <div
        style={{ width: "40%", border: "1px solid #ccc", marginRight: "50px" }}
      >
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            marginBottom: "20px",
          }}
        >
          <img
            src={productData.imageSrc}
            alt="상품 이미지"
            style={{ width: "70%" }}
          />
        </div>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <div
            style={{
              width: "80%",
              height: "20px",
              backgroundColor: "gray",
              borderRadius: "10px",
            }}
          >
            <div
              style={{
                width:
                  productInfo.productState === "AUCTION" ? "60%" : "100%",
                height: "100%",
                backgroundColor:
                  productInfo.productState === "AUCTION" ? "green" : "red",
                borderRadius: "10px",
                textAlign: "center",
              }}
            >
              {productInfo.productState === "AUCTION"
                ? "경매 진행중"
                : "경매 종료"}
            </div>
          </div>
        </div>
      </div>
      <div style={{ width: "50%", border: "1px solid #ccc", padding: "20px" }}>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>코드번호:</span>{" "}
          {productInfo.id}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>카테고리:</span>{" "}
          {productData.category} 미구현
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>상품명:</span> {productInfo.name}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>현재 최고 입찰가:</span>{" "}
          {parsingPrice(productInfo.currentPrice)}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>종료시간:</span>{" "}
          {parsingDate(productInfo.endDate)}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>판매자:</span>{" "}
          {productInfo.sellerName}
        </div>
        <button style={{ marginBottom: "20px" }}>입찰하기</button>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>상품 설명:</span>{" "}
          {productInfo.description}
        </div>
      </div>
    </div>
  );
};

export default AuctionDetailPage
