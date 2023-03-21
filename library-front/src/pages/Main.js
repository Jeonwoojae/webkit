import React, { useEffect, useState } from "react";
import axios from "axios";
import RecommendSlide from "../components/slide/RecommendSlide";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import "./Main.css";
import SearchBox from "../components/search/SearchBox";
import Tabel from "../components/table/Tabel";

function Main() {
  const [recentBooks, setRecentBooks] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/book/least`)
      .then((res) => {
        setRecentBooks(
          res.data.map((book) => ({
            id: book.id,
            title: book.title,
            category: book.category,
            price: book.price,
          }))
        );
      });
  }, []);

  return (
    <div>
      <div className="main-text">
        <ArrowRightIcon fontSize="30px" />
        추천 도서
      </div>
      <RecommendSlide />
      <SearchBox/>
      <div className="main-text">
        <ArrowRightIcon fontSize="30px" />
        HOME
      </div>
      <div className="division-line"></div>
      <Tabel books={recentBooks} />
    </div>
  );
}

export default Main;
