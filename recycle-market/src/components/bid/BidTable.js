import React, { useState } from "react";
import "./BidTable.css";
import call from "../../service/ApiService";
import { useNavigate } from "react-router-dom";
import { Pagination } from "@mui/material";
import parsingPrice from "../../service/ParsingPrice";

const BidTable = ({ data, setData }) => {
  const navigate = useNavigate();
  const [page, setPage] = useState(1);
  const itemsPerPage = 6;
  const pages = Math.ceil(data.length / itemsPerPage);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentBid, setCurrentBid] = useState(null);
  const [highestBid, setHighestBid] = useState(null);
  const [newBid, setNewBid] = useState("");
  const [productId, setProductId] = useState(null);

  const handleEditClick = (bid, productId) => {
    // 서버에서 현재 판매품의 최고 입찰가 확인
    call(`/api/v1/products/${productId}`, "GET", null)
      .then((data) => {
        console.log(data);
        const currentPrice = Number(data.currentPrice);
        setHighestBid(currentPrice);
        setCurrentBid(bid);
        setProductId(productId);
        setIsModalOpen(true);
      })
      .catch((error) => {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
  };

  const handleChange = (event, value) => {
    setPage(value);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setCurrentBid(null);
    setNewBid("");
  };

  const handleBidChange = (event) => {
    setNewBid(event.target.value);
  };

  const handleBidSubmit = () => {
    const newPrice = Number(newBid);
    if (newPrice > currentBid) {
      console.log(`Current bid: ${currentBid}`);
      console.log(`New bid: ${newBid}`);

      call(`/api/v1/bids/${productId}?price=${newBid}`, "PATCH", null)
        .then(() => {
          // 성공적으로 입찰을 추가하면 데이터를 다시 불러옵니다.
          call("/api/v1/bids", "GET", null).then((data) => {
            setData(data);
          });
        })
        .catch((error) => {
          console.error("입찰을 진행하는 중 오류가 발생했습니다.", error);
        });

      handleModalClose();
    } else {
      alert("최고 입찰가보다 높게 설정해야합니다");
    }
  };

  const handleDeleteClick = (productId, bidId) => {
    // 서버에 입찰 삭제 요청
    call(`/api/v1/bids/${productId}`, "DELETE", null)
      .then((response) => {
        console.log(response);
      })
      .then(() => {
        const index = data.findIndex((item) => item.id === bidId); // 삭제할 항목의 index를 찾습니다.
        if (index !== -1) {
          // 삭제할 항목이 있을 경우
          const newData = [...data]; // 새로운 배열을 생성합니다.
          newData.splice(index, 1); // 배열에서 해당 항목을 제거합니다.
          setData(newData); // state를 업데이트합니다.
        }
      })
      .catch((error) => {
        console.error("입찰을 삭제하는 중 오류가 발생했습니다.", error);
      });
  };

  const handleRowClick = (itemId) => {
    const path = `/product/${itemId}`;
    navigate(path);
  };

  return (
    <>
      <div className="table-container">
        <table className="bid-table">
          <thead>
            <tr>
              <th>상품코드</th>
              <th>상품명</th>
              <th>내 입찰가</th>
              <th>관리</th>
            </tr>
          </thead>
          <tbody>
            {data
              .slice((page - 1) * itemsPerPage, page * itemsPerPage)
              .map((item) => (
                <tr
                  key={item.id}
                  
                >
                  <td onClick={() => handleRowClick(item.productId)}>{item.productId}</td>
                  <td onClick={() => handleRowClick(item.productId)}>{item.productName}</td>
                  <td onClick={() => handleRowClick(item.productId)}>{parsingPrice(item.price)}원</td>
                  <td>
                    {item.end ? (
                      <>종료된 경매</>
                    ) : (
                      <>
                        <button
                          className="btn-edit"
                          onClick={() =>
                            handleEditClick(item.price, item.productId)
                          }
                        >
                          수정하기
                        </button>
                        <button
                          className="btn-delete"
                          onClick={() =>
                            handleDeleteClick(item.productId, item.id)
                          }
                        >
                          삭제하기
                        </button>
                      </>
                    )}
                  </td>
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
      {isModalOpen && (
        <div className="modal">
          <div className="modal-content">
            <span className="modal-close" onClick={handleModalClose}>
              &times;
            </span>
            <h2>입찰가 수정하기</h2>
            <p>내 입찰가: {parsingPrice(currentBid)}원</p>
            <p>최고 입찰가: {parsingPrice(highestBid)}원</p>
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
            <button className="btn-submit" onClick={handleBidSubmit}>
              수정하기
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default BidTable;
