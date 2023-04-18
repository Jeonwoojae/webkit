import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import call from "../service/ApiService";
import parsingDate from "../service/ParsingDate";
import parsingPrice from "../service/ParsingPrice";
import "./AuctionDetailPage.css"

const AuctionDetailPage = () => {
  const { id } = useParams();
  const [productInfo, setProductInfo] = useState({});
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [highestBid, setHighestBid] = useState(null);
  const [newBid, setNewBid] = useState("");

  const handleEditClick = () => {
    console.log("버튼클릭");
    // 서버에서 현재 판매품의 최고 입찰가 확인
    const currentPrice = Number(productInfo.currentPrice);
    setHighestBid(currentPrice);
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setNewBid("");
  };

  const handleBidChange = (event) => {
    setNewBid(event.target.value);
  };

  const handleBidSubmit = () => {
    const newPrice = Number(newBid);
    if (newPrice > highestBid) {
      console.log(`Current bid: ${highestBid}`);
      console.log(`New bid: ${newBid}`);

      call(`/api/v1/bids/${id}?price=${newBid}`, "POST", null)
        .then(() => {
          // 성공적으로 입찰을 추가하면 데이터를 다시 불러옵니다.
          handleModalClose();
        })
        .catch((error) => {
          console.error("입찰을 진행하는 중 오류가 발생했습니다.", error);
        });

      handleModalClose();
    } else {
      alert("최고 입찰가보다 높게 설정해야합니다");
    }
  };

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
    description: "this is my product",
  };

  return (
    <>
      <div className="auction-detail-container">
        <div className="auction-image-container">
          <img
            className="auction-image"
            src={productData.imageSrc}
            alt="상품 이미지"
          />
          <div className={`auction-status ${productInfo.productState}`}>
            {productInfo.productState === "AUCTION"
              ? "경매 진행중"
              : "경매 종료"}
          </div>
        </div>
        <div className="auction-info-container">
          <div className="auction-info-row">
            <span className="auction-info-label">코드번호:</span>{" "}
            {productInfo.id}
          </div>
          <div className="auction-info-row">
            <span className="auction-info-label">카테고리:</span>{" "}
            {productData.category} 미구현
          </div>
          <div className="auction-info-row">
            <span className="auction-info-label">상품명:</span>{" "}
            {productInfo.name}
          </div>
          <div className="auction-info-row">
            <span className="auction-info-label">현재 최고 입찰가:</span>{" "}
            {parsingPrice(productInfo.currentPrice)}
          </div>
          <div className="auction-info-row">
            <span className="auction-info-label">종료시간:</span>{" "}
            {parsingDate(productInfo.endDate)}
          </div>
          <div className="auction-info-row">
            <span className="auction-info-label">판매자:</span>{" "}
            {productInfo.sellerName}
          </div>
          <button
            className="auction-bid-button"
            onClick={() => handleEditClick()}
          >
            입찰하기
          </button>
          <div className="auction-info-row">
            <span className="auction-info-label">상품 설명:</span>{" "}
            {productInfo.description}
          </div>
        </div>
      </div>
      {isModalOpen && (
        <div className="modal">
          <div className="modal-content">
            <span className="modal-close" onClick={handleModalClose}>
              &times;
            </span>
            <h2>입찰하기</h2>
            <p>최고 입찰가: {highestBid}원</p>
            <div className="form-group">
              <label htmlFor="newBid">새로운 입찰가:</label>
              <input
                type="number"
                id="newBid"
                name="newBid"
                value={newBid}
                onChange={handleBidChange}
              />
            </div>
            <p>입찰한 기록이 있으면 내 입찰에서 수정해주세요.</p>
            <button className="btn-submit" onClick={handleBidSubmit}>
              수정하기
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default AuctionDetailPage;
