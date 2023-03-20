import React from "react";
import RecommendSlide from "../components/slide/RecommendSlide";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import "./Main.css"

function Main() {
  return (
    <div>
      <div className="recommend-book-text">
        <ArrowRightIcon fontSize="30px" />
        추천 도서
      </div>
      <RecommendSlide />
      <div>검색창 구역</div>
      <div>선택한 메뉴를 표시</div>
      <div className="division-line"></div>
      <div>최근 추가된 책 목록</div>
      <div className="division-line"></div>
    </div>
  );
}

export default Main;
