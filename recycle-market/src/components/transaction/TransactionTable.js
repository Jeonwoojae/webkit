import React from "react";
import { Link } from "react-router-dom";

const TransactionTable = ({ products }) => {
  return (
    <table>
      <thead>
        <tr>
          <th>상품코드</th>
          <th>낙찰자</th>
          <th>낙찰가격</th>
          <th>판매자</th>
          <th>거래상태</th>
        </tr>
      </thead>
      <tbody>
        {products.map((product) => (
          <tr key={product.code}>
            <td>
              <Link to={`/products/${product.code}`}>{product.code}</Link>
            </td>
            <td>{product.winner}</td>
            <td>{product.price}</td>
            <td>{product.seller}</td>
            <td>{product.status}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TransactionTable;