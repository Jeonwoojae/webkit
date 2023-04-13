import React from "react";
import PropTypes from "prop-types";
import "./DealInfo.css";

const DealInfo = ({ product }) => {
  const { image, code, category, name, price, seller } = product;

  return (
    <div className="deal-info-container">
      <div className="deal-info-image">
        <img src={image} alt={name} />
      </div>
      <div className="deal-info-details">
        <div className="deal-info-code">상품코드: {code}</div>
        <div className="deal-info-category">카테고리: {category}</div>
        <div className="deal-info-name">{name}</div>
        <div className="deal-info-price">입찰가: {price}</div>
        <div className="deal-info-seller">판매자: {seller}</div>
        <div className="deal-info-buttons">
          <button className="deal-info-button-pay">결제하기</button>
          <button className="deal-info-button-cancel">거래취소</button>
        </div>
      </div>
    </div>
  );
};

DealInfo.propTypes = {
  product: PropTypes.shape({
    image: PropTypes.string.isRequired,
    code: PropTypes.string.isRequired,
    category: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    seller: PropTypes.string.isRequired,
  }).isRequired,
};

export default DealInfo;
