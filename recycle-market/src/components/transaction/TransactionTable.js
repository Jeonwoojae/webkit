import "./TransactionTable.css";
import { useNavigate } from "react-router-dom";
import parsingPrice from "../../service/ParsingPrice";
import { Pagination } from "@mui/material";
import { useState } from "react";

const TransactionTable = ({ transactions }) => {
  const navigate = useNavigate();
  const [page, setPage] = useState(1);
  const itemsPerPage = 6;
  const pages = Math.ceil(transactions.length / itemsPerPage);

  const handleRowClick = (transactionId) => {
    const path = `/transaction/detail/${transactionId}`;
    navigate(path);
  };
  const handleChange = (event, value) => {
    setPage(value);
  };

  return (
    <>
      <div className="table-container">
        <table className="transaction-table">
          <thead>
            <tr>
              <th>상품코드</th>
              <th>상품명</th>
              <th>낙찰자</th>
              <th>낙찰가격</th>
              <th>판매자</th>
              <th>거래상태</th>
            </tr>
          </thead>
          <tbody>
            {transactions
              .slice((page - 1) * itemsPerPage, page * itemsPerPage)
              .map((transaction) => (
                <tr
                  key={transaction.id}
                  onClick={() => handleRowClick(transaction.id)}
                >
                  <td>{transaction.id}</td>
                  <td>{transaction.productName}</td>
                  <td>{transaction.buyerName}</td>
                  <td>{parsingPrice(transaction.price)}원</td>
                  <td>{transaction.sellerName}</td>
                  <td>{transaction.transactionState}</td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
      <Pagination
        className="select-page"
        count={pages}
        page={page}
        onChange={handleChange}
      />
    </>
  );
};

export default TransactionTable;
