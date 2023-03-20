import React from "react";
import RecommendSlide from "../components/slide/RecommendSlide";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import "./Main.css";
import SearchBox from "../components/search/SearchBox";
import Tabel from "../components/table/Tabel";

function Main() {
  return (
    <div>
      <div className="recommend-book-text">
        <ArrowRightIcon fontSize="30px" />
        추천 도서
      </div>
      <RecommendSlide />
      <SearchBox />
      <div>선택한 메뉴를 표시</div>
      <div className="division-line"></div>
      <Tabel />
      <div className="division-line"></div>
    </div>
  );
}

export default Main;
