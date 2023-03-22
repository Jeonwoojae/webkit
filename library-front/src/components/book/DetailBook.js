import React, { useState } from "react";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import axios from "axios";

function DetailBook(props) {
  var data = [
    { index: "제목", value: props.bookInfo.title },
    { index: "카테고리", value: props.bookInfo.category },
    { index: "국가", value: props.bookInfo.nation },
    { index: "장르", value: props.bookInfo.genre },
    { index: "가격", value: props.bookInfo.price },
    { index: "입력일", value: props.bookInfo.createdAt },
  ];

  function goHome(e) {
    window.location.replace("/");
  }

  function deleteHandler(bookId) {
    axios
      .delete(`http://localhost:8080/book/${bookId}`)
      .then((res) => {
        alert("삭제가 완료되었습니다.");
        goHome(res);
      })
      .catch((e)=>{
        alert("삭제에 실패했습니다.");
        goHome(e);
      });
  }

  return (
    <div>
      <ArrowRightIcon fontSize="30px" />책 상세
      <div>
        <table>
          <tbody>
            {data.map((item) => {
              return (
                <tr>
                  <td>{item.index}</td>
                  <td>{item.value}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
        <button onClick={() => props.setAccessMode(true)}>수정</button>
        <button onClick={() => deleteHandler(props.bookInfo.id)}>삭제</button>
      </div>
    </div>
  );
}

export default DetailBook;
