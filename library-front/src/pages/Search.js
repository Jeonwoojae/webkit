import axios from "axios";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import ResultTable from "../components/search/ResultTable";
import SearchBox from "../components/search/SearchBox";
import "./Search.css"

function Search() {
  const [searchBooks, setSearchBooks] = useState([]);
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  var isCondition;

  var condition = params.get("c");
  var query = params.get("q");

  var nation = null;
  var genre = null;

  if (condition == null) {
    nation = params.get("nation");
    genre = params.get("genre");
    isCondition = false;
  } else {
    isCondition = true;
  }


  useEffect(() => {
    if(isCondition)
    {axios.get(`http://localhost:8080/book/search/${condition}/${query}`)
      .then((res) => {
        if(res!=null)
        {setSearchBooks(
          res.data.map((book) => ({
            id: book.id,
            title: book.title,
            category: book.category,
            price: book.price,
          }))
        );} else setSearchBooks(null);
      }).catch((err) => {
        alert("찾을 수 없습니다");
      });}
      else if(genre == null){axios.get(`http://localhost:8080/book/search/nation/${nation}`)
      .then((res) => {
        setSearchBooks(
          res.data.map((book) => ({
            id: book.id,
            title: book.title,
            category: book.category,
            price: book.price,
          }))
        );
      }).catch((err) => {
        alert("찾을 수 없습니다");
      });}
      else {axios.get(`http://localhost:8080/book/search/nation/${nation}/genre/${genre}`)
      .then((res) => {
        setSearchBooks(
          res.data.map((book) => ({
            id: book.id,
            title: book.title,
            category: book.category,
            price: book.price,
          }))
        );
      }).catch((err) => {
        alert("찾을 수 없습니다");
      });}
  }, [condition, genre, isCondition, query, nation]);

  console.log(searchBooks);

  return (
    <div className="result-box">
      <SearchBox />
      <div className="result-text">
        {isCondition ? (
          <>
            {query} 검색 결과
          </>
        ) : (
          <>
            {nation} / {genre}
          </>
        )}
      </div>
      <p />
      <div className="nav-division-line"></div>
      <ResultTable books={searchBooks} />
    </div>
  );
}

export default Search;
