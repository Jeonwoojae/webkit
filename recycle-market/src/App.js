import React, { useEffect, useState } from "react";
import {
  Grid,
  Button,
  Select,
  InputLabel,
  FormControl,
  MenuItem,
  TextField,
} from "@material-ui/core";
import { Pagination } from "@mui/material";
import "./App.css";
import call from "./service/ApiService";
import { Link } from "react-router-dom";
import parsingPrice from "./service/ParsingPrice";
import parsingDate from "./service/ParsingDate";
import noimage from './assets/noImage.png'

function App() {
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState("");
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const itemsPerPage = 6;
  const pages = Math.ceil(products.length / itemsPerPage);

  useEffect(() => {
    call(`/api/v1/products`, "GET", null)
      .then((data) => {
        console.log(data);
        setProducts(data); // 받아온 데이터를 상태값으로 설정
      })
      .catch((error) => {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
  }, []); // 컴포넌트가 처음 렌더링될 때만 실행

  const handleChange = (event, value) => {
    setPage(value);
  };
  const handleCategoryChange = (event) => {
    setCategory(event.target.value);
  };

  const handleSearchChange = (event) => {
    setSearch(event.target.value);
  };

  const handleSearch = () => {
    const params = {};
    if (category != "") {
      params.category = category;
    }
    if (search != "") {
      params.name = search;
    }

    call(`/api/v1/products/search`, "GET", params)
      .then((data) => {
        console.log(data);
        setProducts(data); // 받아온 데이터를 상태값으로 설정
      })
      .catch((error) => {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다.", error);
      });
    console.log(`Search category: ${category}, keyword: ${search}`);
  };

  const handleSell = () => {
    console.log("Go to sell page");
    window.location.href = "/sell";
  };

  return (
    <div>
      <div className="search-bar">
        <FormControl variant="outlined">
          <InputLabel id="category-select-label">카테고리 선택</InputLabel>
          <Select
            labelId="category-select-label"
            id="category-select"
            value={category}
            onChange={handleCategoryChange}
            label="카테고리 선택"
          >
            <MenuItem value={null} disabled>
              전체
            </MenuItem>
            <MenuItem value="의류">의류</MenuItem>
            <MenuItem value="음식">음식</MenuItem>
            <MenuItem value="운동용품">운동용품</MenuItem>
            <MenuItem value="전자제품">전자제품</MenuItem>
            <MenuItem value="기타">기타</MenuItem>
          </Select>
        </FormControl>

        <TextField
          className="search-input"
          label="상품 검색"
          variant="outlined"
          value={search}
          onChange={handleSearchChange}
        />

        <Button
          className="search-btn"
          variant="contained"
          color="primary"
          onClick={handleSearch}
          style={{marginLeft:"10px"}}
        >
          검색
        </Button>

        {localStorage.getItem("ROLE") == "SELLER" ? (
          <Button
            className="cell-btn"
            variant="contained"
            color="secondary"
            onClick={handleSell}
            style={{marginLeft:"10px"}}
          >
            판매하기
          </Button>
        ) : (
          <></>
        )}
      </div>

      {products.length == 0 ? (
        <>
        <h1 style={{textAlign: "center"}}>NOT FOUND..</h1>
        <p style={{textAlign: "center"}}>찾으시는 상품이 없습니다.</p>
        </>
      ) : (
        <>
          <Grid container spacing={3} className="centered-grid-container">
            {products
              .slice((page - 1) * itemsPerPage, page * itemsPerPage)
              .map((product) => (
                <Grid item xs={6} sm={4} md={3} key={product.id} style={{margin:"20px"}} className="container-item">
                  <Link
                    to={`/product/${product.id}`}
                    style={{ color: "black", textDecoration: "none" }}
                  >
                    <img
                      width="200"
                      height="200"
                      src={product.imageUrl}
                      alt={"상품 이미지"}
                      onError={(e) => e.target.src = noimage}
                    />
                    <p>{product.name}</p>
                    <p>현재 {parsingPrice(product.currentPrice)}원</p>
                    <p>{parsingDate(product.endDate)}까지</p>
                  </Link>
                </Grid>
              ))}
          </Grid>
          <Pagination
            className="select-page"
            count={pages}
            page={page}
            onChange={handleChange}
          />
        </>
      )}
    </div>
  );
}

export default App;
