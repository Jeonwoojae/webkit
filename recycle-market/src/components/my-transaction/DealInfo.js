import React, { useState } from "react";
import "./DealInfo.css";
import parsingPrice from "../../service/ParsingPrice";
import call from "../../service/ApiService";

const DealInfo = ({ transaction }) => {
  const {
    image,
    id,
    category,
    productName,
    price,
    sellerName,
    buyerName,
    transactionState,
  } = transaction;
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [trackingNumber, setTrackingNumber] = useState("");

  const handlePayment = () => {
    const paymentWindow = window.open("", "Payment Window",'width=500, height=200');
    paymentWindow.document.write(`
      <h2>결제 정보</h2>
      <p>상품명: ${transaction.productName}</p>
      <p>낙찰가: ${parsingPrice(transaction.price)}원</p>
      <button id="card-payment-button">카드결제</button>
      <button id="bank-transfer-button">통장입금</button>
    `);
  
    const cardPaymentButton = paymentWindow.document.getElementById("card-payment-button");
    const bankTransferButton = paymentWindow.document.getElementById("bank-transfer-button");
  
    cardPaymentButton.addEventListener("click", () => {
      // 카드 결제 통신
      call(`/api/v1/transactions/${transaction.id}/pay?method=CARD`, "PATCH", null)
      .then((data) => {
        alert("결제가 완료되었습니다!");
        paymentWindow.close();
        window.location.reload();
      })
      .catch((error) => {
        alert("결제 중 오류가 발생했습니다.", error)
      });
    });
  
    bankTransferButton.addEventListener("click", () => {
      // 통장 결제 통신
      call(`/api/v1/transactions/${transaction.id}/pay?method=BANK_TRANSFER`, "PATCH", null)
      .then((data) => {
        alert("결제가 완료되었습니다!");
        paymentWindow.close();
        window.location.reload();
      })
      .catch((error) => {
        alert("결제 중 오류가 발생했습니다.", error)
      });
    });
  };

  const handleCancel = () => {
    // 결제 취소 버튼 클릭 시 처리
    const confirmed = window.confirm('결제를 취소하시겠습니까?');
    if (confirmed) {
      // 결체 취소 처리
      call(`/api/v1/transactions/${transaction.id}/cancle`, "PATCH", null)
      .then((data) => {
        alert("취소가 완료되었습니다!");
        window.location.reload();
      })
      .catch((error) => {
        alert("취소 중 오류가 발생했습니다.", error)
      });
    }
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setTrackingNumber("");
  };

  const handleNumberChange = (event) => {
    setTrackingNumber(event.target.value);
  };

  const handleTrackingNumberSubmit = () => {

      console.log(`${trackingNumber}`);
      call(`/api/v1/transactions/${transaction.id}/ship`, "PATCH", parseInt(trackingNumber))
        .then(() => {
          handleModalClose();
          window.location.reload();
        })
        .catch((error) => {
          console.error("등록을 진행하는 중 오류가 발생했습니다.", error);
        });

      handleModalClose();
  };

  const handleShipping = () => {
    // 운송장 등록 버튼 클릭 시 처리
    setIsModalOpen(true);
  };

  const handleDeliveryConfirmation = () => {
    // 수취 확인 버튼 클릭 시 처리
    const confirmed = window.confirm('수취확인 하시겠습니까?');
    if (confirmed) {
      // 결체 취소 처리
      call(`/api/v1/transactions/${transaction.id}/complete`, "PATCH", null)
      .then((data) => {
        alert("수취 확인이 완료되었습니다!");
        window.location.reload();
      })
      .catch((error) => {
        alert("수취 확인 중 오류가 발생했습니다.", error)
      });
    }
  };

  const handleDeliveryCheck = () => {
    const paymentWindow = window.open("", "운송장 정보",'width=500, height=200');
    paymentWindow.document.write(`
      <h2>결제 정보</h2>
      <p>상품명: ${transaction.productName}</p>
      <p>운송장 번호 : ${transaction.trackingNumber}</p>
    `);
  }

  return (
    <>
    <div className="deal-info-container">
      <div className="deal-info-image">
        <img src={image} alt="https://via.placeholder.com/150" />
      </div>
      <div className="deal-info-details">
        <div className="deal-info-code">상품코드 : {id}</div>
        <div className="deal-info-category">카테고리 : {category}</div>
        <div className="deal-info-name">상품명 : {productName}</div>
        <div className="deal-info-buyer">낙찰자 : {buyerName}</div>
        <div className="deal-info-price">낙찰가 : {parsingPrice(price)}원</div>
        <div className="deal-info-seller">판매자 : {sellerName}</div>
        <div className="deal-info-buttons">
          {transactionState === "결제 대기" && (
            <>
              <button className="deal-info-button-pay" onClick={handlePayment}>
                결제하기
              </button>
              <button
                className="deal-info-button-cancel"
                onClick={handleCancel}
              >
                결제취소
              </button>
            </>
          )}
          {transactionState === "결제 완료" && (
            <button
              className="deal-info-button-pay"
              onClick={handleShipping}
            >
              운송장 등록
            </button>
          )}
          {transactionState === "배송 중" && (
            <>
              <button
                className="deal-info-button-pay"
                onClick={handleDeliveryCheck}
              >
                운송장 확인
              </button>
              <button
                className="deal-info-button-cancel"
                onClick={handleDeliveryConfirmation}
              >
                수취 확인
              </button>
            </>
          )}
          {transactionState === "거래 완료" && <div>거래 완료</div>}
          {transactionState === "거래 취소" && <div>거래 취소</div>}
        </div>
      </div>
    </div>
    {isModalOpen && (
      <div className="modal">
        <div className="modal-content">
          <span className="modal-close" onClick={handleModalClose}>
            &times;
          </span>
          <h2>운송장 등록</h2>
          <div className="form-group">
            <label htmlFor="trackingNumber">운송장 번호:</label>
            <input
              type="number"
              id="trackingNumber"
              name="trackingNumber"
              value={trackingNumber}
              onChange={handleNumberChange}
            />
          </div>
          <button className="btn-submit" onClick={handleTrackingNumberSubmit}>
            등록하기
          </button>
        </div>
      </div>
    )}
    </>
  );
};

export default DealInfo;
