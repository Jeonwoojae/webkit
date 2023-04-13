import React from "react";

const AuctionDetailPage = () => {
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
                  productData.auctionStatus === "in progress" ? "60%" : "100%",
                height: "100%",
                backgroundColor:
                  productData.auctionStatus === "in progress" ? "green" : "red",
                borderRadius: "10px",
                textAlign: "center",
              }}
            >
              {productData.auctionStatus === "in progress"
                ? "경매 진행중"
                : "경매 종료"}
            </div>
          </div>
        </div>
      </div>
      <div style={{ width: "50%", border: "1px solid #ccc", padding: "20px" }}>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>코드번호:</span>{" "}
          {productData.code}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>카테고리:</span>{" "}
          {productData.category}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>상품명:</span> {productData.name}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>현재 최고 입찰가:</span>{" "}
          {productData.currentBid}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>종료시간:</span>{" "}
          {productData.endTime}
        </div>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>판매자:</span>{" "}
          {productData.seller}
        </div>
        <button style={{ marginBottom: "20px" }}>입찰하기</button>
        <div style={{ marginBottom: "20px" }}>
          <span style={{ fontWeight: "bold" }}>상품 설명:</span>{" "}
          {productData.description}
        </div>
      </div>
    </div>
  );
};

export default AuctionDetailPage
